package cat.itb.m78.exercices.API

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import coil3.compose.AsyncImage
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import m78exercices.composeapp.generated.resources.Res

object Destination {
    @Serializable
    data object ListScreen
    @Serializable
    data object EmbScreen
}

@Serializable
data class Emb(
    @SerialName("dia") val day : String,
    @SerialName("estaci") val name: String,
    @SerialName("nivell_absolut") val nAbsolut: Double,
    @SerialName("percentatge_volum_embassat") val perc: Double,
    @SerialName("volum_embassat") val volume: Double
)
//API
object MyApi {
    private const val URL = "https://analisi.transparenciacatalunya.cat/resource/gn9e-3qhr.json?"
    private val client = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
    suspend fun list() = client.get(URL).body<List<Emb>>()
}

class EmbVM : ViewModel() {
    val embList = mutableStateOf<List<Emb>>(emptyList())
    private val apiCharged = mutableStateOf(false)

    init {
        viewModelScope.launch(Dispatchers.Default) {
            embList.value = MyApi.list()
            apiCharged.value = true
            reassignEmbListPositions()
        }
    }

    //SETTINGS
    private val settings: Settings = Settings()

    private var currentEmbName: String? = settings.getStringOrNull("name")
    private var currentPosition: Int = settings.getInt(
        "pos",
        defaultValue = 0
    )
    private val currentEmbPosition = mutableStateOf<Int?>(null)

    fun assignCurrentEmb(embName : String) {
        var position = 0
        currentEmbName = embName
        settings.putString("name", embName)

        for (emb in embList.value){
            if (emb.name == embName){
                currentEmbPosition.value = position
                settings.putInt("pos", position)
            }
            position += 1
        }
    }
    fun reassignEmbListPositions() {
        if (apiCharged.value){
            val embListMutable = embList.value.toMutableList()
            val firstEmbOG = embListMutable[0]

            for (emb in embList.value){
                if (emb.name == currentEmbName){
                    embListMutable[0] = emb
                    embListMutable[currentPosition] = firstEmbOG
                }
            }
            embList.value = embListMutable
        }
    }
    fun searchEmbByName() : Emb? {
        for (emb in embList.value){
            if (emb.name == currentEmbName){
                return emb
            }
        }
        return null
    }
}

@Composable
fun ListScreen(goToEmbScreen:() -> Unit){
    val embVM = viewModel { EmbVM() }
    ListScreenArguments(goToEmbScreen, embVM.embList.value, embVM :: assignCurrentEmb)
}
@Composable
fun ListScreenArguments(goToEmbScreen:() -> Unit, emb: List<Emb>, assignEmb: (String) -> Unit){
    LazyColumn(modifier = Modifier.padding(15.dp), verticalArrangement = Arrangement.spacedBy(15.dp)) {
        items(emb) {embass ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(end = 15.dp).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button( onClick = {assignEmb(embass.name)
                        goToEmbScreen()}
                    ){
                        Text(embass.name)
                    }
                }
            }
        }
    }
}

@Composable
fun EmbScreen(goToListScreen:() -> Unit){
    val embVM = viewModel { EmbVM() }
    EmbScreenArguments(goToListScreen, embVM :: reassignEmbListPositions, embVM :: searchEmbByName)
}
@Composable
fun EmbScreenArguments(goToListScreen: () -> Unit, reassign:() -> Unit, searchEmb:() -> Emb?) {
    val currentEmb = searchEmb()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (currentEmb != null) {
            Text("Nom: " + currentEmb.name)
            Text("Dia: " + currentEmb.day)
            Text("Nivell absolut: " + currentEmb.nAbsolut)
            Text("Percentatge volum: " + currentEmb.perc)
            Text("Volum embassat: " + currentEmb.volume)
        } else {
            // Si currentEmb és null, no mostrem res o mostrem un missatge.
            Text("No hi ha informació disponible.")
        }
        Spacer(Modifier.height(20.dp))
        Button( onClick = {goToListScreen()
            reassign()}){
            Text("Torna a la llista")
        }
    }
}

@Composable
fun EmbNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destination.ListScreen) {
        composable<Destination.ListScreen> {
            ListScreen( goToEmbScreen = {navController.navigate(Destination.EmbScreen)})
        }
        composable<Destination.EmbScreen> {
            EmbScreen( goToListScreen = {navController.navigate(Destination.ListScreen)})
        }
    }
}