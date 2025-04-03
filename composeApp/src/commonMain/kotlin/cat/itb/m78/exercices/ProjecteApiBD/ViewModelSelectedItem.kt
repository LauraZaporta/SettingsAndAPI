package cat.itb.m78.exercices.ProjecteApiBD

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.itb.m78.exercices.db.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class SingleSorceryWithFav(
    val singleSorcery: SingleSorcery,
    val isFav: Boolean
)

class SorceriesVM(id: String): ViewModel() {
    private val sorceriesDB = database.sorceriesDBQueries
    val sorcery = mutableStateOf<SingleSorceryWithFav?>(null)

    fun changeFavState(id : String){
        // Actualitza la BD, no la vista
        if (isFav(id)){
            sorceriesDB.deleteFav(id)
        } else {
            sorceriesDB.insertFav(id)
        }
        // Actualitza l'estat de la vista
        val currentSorcery = sorcery.value
        currentSorcery?.let {
            val newFavState = !it.isFav
            sorcery.value = it.copy(isFav = newFavState)
        }
    }
    private fun isFav(id: String): Boolean {
        val favs = database.sorceriesDBQueries.selectFavs().executeAsList()
        return id in favs
    }
    init {
        viewModelScope.launch(Dispatchers.Default) {
            val detail = SorceriesAPI(id).detail()
            val isFav = isFav(detail.data.id)
            sorcery.value = SingleSorceryWithFav(detail, isFav)
        }
    }
}