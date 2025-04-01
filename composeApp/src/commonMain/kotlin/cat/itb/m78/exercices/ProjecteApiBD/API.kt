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
data class SorceryList(
    @SerialName("data") val data : List<SorceryData>
)
@Serializable
data class SingleSorcery(
    @SerialName("data") val data : SorceryData
)
@Serializable
data class SorceryData(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("image") val imgUrl: String,
    @SerialName("description") val desc: String,
    @SerialName("effects") val effect: String
)

class SorceriesAPI(id : String) {
    private val url = "https://eldenring.fanapis.com/api/sorceries?limit=100"
    private val urlDetail = "https://eldenring.fanapis.com/api/sorceries/$id"
    private val client = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun list(): SorceryList {
        return client.get(url).body()
    }
    suspend fun detail(): SingleSorcery {
        return client.get(urlDetail).body()
    }
}