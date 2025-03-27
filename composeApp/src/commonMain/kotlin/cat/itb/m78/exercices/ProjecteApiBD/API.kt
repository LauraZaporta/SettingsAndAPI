package cat.itb.m78.exercices.ProjecteApiBD

import cat.itb.m78.exercices.API.Joke
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

//https://eldenring.fanapis.com/api/sorceries/17f69526092l0hykrkfqubdcrri5ri
@Serializable
data class Sorcery(
    @SerialName("Data") val data : SorceryData
)
@Serializable
data class SorceryData(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("image") val imgUrl: String,
    @SerialName("description") val desc: String,
    @SerialName("effects") val effect: String
)

object SorceriesAPI {
    private val url = "https://eldenring.fanapis.com/api/sorceries?limit=100"
    private val client = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun list() = client.get(url).body<List<Sorcery>>()
}