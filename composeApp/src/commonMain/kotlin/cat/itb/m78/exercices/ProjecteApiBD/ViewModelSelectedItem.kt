package cat.itb.m78.exercices.ProjecteApiBD

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SorceriesVM(id: String): ViewModel() {
    val sorcery = mutableStateOf<SingleSorcery?>(null)

    init {
        viewModelScope.launch(Dispatchers.Default) {
            sorcery.value = SorceriesAPI(id).detail()
        }
    }
}