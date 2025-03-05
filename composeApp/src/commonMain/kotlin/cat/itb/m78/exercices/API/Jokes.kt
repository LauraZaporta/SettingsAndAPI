package cat.itb.m78.exercices.API

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Serializable
data class Joke(
    @SerialName("setup") val setup : String,
    @SerialName("punchline") val punchline: String
)

class JokeVM : ViewModel() {
    object MyApi {
        private val url = "https://api.sampleapis.com/jokes/goodJokes"
        private val client = HttpClient() {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        suspend fun list() = client.get(url).body<List<Joke>>()
    }

    private var jokeList = listOf<Joke>()
    val joke: MutableState<Joke?> = mutableStateOf(null)

    private fun generateJoke() {
        // Ensure joke list is not empty before accessing it
        if (jokeList.isNotEmpty()) {
            val randomJokeIndex = Random.nextInt(jokeList.size) // Random index based on list size
            joke.value = jokeList[randomJokeIndex]
        }
    }

    init {
        viewModelScope.launch(Dispatchers.Default) {
            jokeList = MyApi.list() // Fetch jokes from API
            generateJoke() // Generate a random joke once the list is fetched
        }
    }
}

@Composable
fun Jokes() {
    val jokeVM = viewModel { JokeVM() }

    val joke = jokeVM.joke.value

    if (joke != null) {
        JokesArguments(joke.setup, joke.punchline)
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()){
            Text("Loading joke...")
        }
    }
}

@Composable
fun JokesArguments(setup : String, punchline : String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()){
        Text(setup)
        Text(punchline)
    }
}