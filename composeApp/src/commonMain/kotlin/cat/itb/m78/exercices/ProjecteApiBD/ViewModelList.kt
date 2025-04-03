package cat.itb.m78.exercices.ProjecteApiBD

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.itb.m78.exercices.db.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SorceriesVMList: ViewModel() {
    private val sorceriesDB = database.sorceriesDBQueries
    private val allSorceries = mutableStateOf<List<SorceryData>>(emptyList()) //Will stay the same after charging the API
    val sorceriesList = mutableStateOf<List<SorceryData>>(emptyList()) // Will vary
    val loading = mutableStateOf(true)
    val searchQuery = mutableStateOf("")

    private fun selectFavsIds() : List<String> {
        return sorceriesDB.selectFavs().executeAsList()
    }

    fun listIsFavs(){
        val favsList = mutableListOf<SorceryData>()
        for (id in selectFavsIds()){
            for (sorcery in allSorceries.value){
                if (sorcery.id == id){
                    favsList.add(sorcery)
                }
            }
        }
        sorceriesList.value = favsList
    }
    fun listIsAll(){
        sorceriesList.value = allSorceries.value
    }

    init {
        viewModelScope.launch(Dispatchers.Default) {
            sorceriesList.value = SorceriesAPI("").list().data
            allSorceries.value = SorceriesAPI("").list().data
            loading.value = false
        }
    }
}