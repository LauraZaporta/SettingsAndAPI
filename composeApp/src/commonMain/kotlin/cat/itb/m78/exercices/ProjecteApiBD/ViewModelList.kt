package cat.itb.m78.exercices.ProjecteApiBD

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SorceriesVMList: ViewModel() {
    val sorceriesList = mutableStateOf<List<SorceryData>>(emptyList())
    val loading = mutableStateOf(true)
    val searchQuery = mutableStateOf("")

    init {
        viewModelScope.launch(Dispatchers.Default) {
            sorceriesList.value = SorceriesAPI("").list().data
            loading.value = false
        }
    }
}