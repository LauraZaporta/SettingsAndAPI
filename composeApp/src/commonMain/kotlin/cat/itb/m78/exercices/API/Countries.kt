package cat.itb.m78.exercices.API

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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
import kotlin.random.Random
import coil3.Image
import coil3.compose.AsyncImage

@Serializable
data class Country(
    @SerialName("name") val name : String,
    @SerialName("capital") val capital: String,
    @SerialName("media") val media: Media
)

@Serializable
data class Media(
    @SerialName("flag") val flag: String //URL BANDERA
)

class CountriesVM : ViewModel() {
    object MyApi {
        private const val URL = "https://api.sampleapis.com/countries/countries"
        private val client = HttpClient() {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
        suspend fun list() = client.get(URL).body<List<Country>>()
    }
    var countryList = listOf<Country>()
    init {
        viewModelScope.launch(Dispatchers.Default) {
            countryList = MyApi.list() // Fetch jokes from API
        }
    }
}

@Composable
fun Countries() {
    val countriesVM = viewModel { CountriesVM() }
    val countries = countriesVM.countryList

    CountriesArgument(countries)
}

@Composable
fun CountriesArgument(countries: List<Country>) {
    LazyColumn(modifier = Modifier.padding(15.dp), verticalArrangement = Arrangement.spacedBy(15.dp)) {
        itemsIndexed(countries) { _, country ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.padding(end = 15.dp), verticalArrangement = Arrangement.spacedBy(15.dp)) {
                        Text(text = country.name)
                        Text(text = country.capital)
                    }
                    AsyncImage(
                        model = country.media.flag,
                        contentDescription = null,
                        modifier = Modifier.height(60.dp).width(80.dp)
                    )
                }
            }
        }
    }
}
