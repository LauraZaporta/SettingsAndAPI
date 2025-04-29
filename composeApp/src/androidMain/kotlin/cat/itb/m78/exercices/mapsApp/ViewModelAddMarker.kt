package cat.itb.m78.exercices.mapsApp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class VMAddMarker : ViewModel(){
    val markerTitle = mutableStateOf("")
    val markerDesc = mutableStateOf("")
    val addImageProcess = mutableStateOf(false)
}