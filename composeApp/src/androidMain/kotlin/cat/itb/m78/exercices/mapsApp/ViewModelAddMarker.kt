package cat.itb.m78.exercices.mapsApp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.db.database

class VMAddMarker : ViewModel(){
    private val barDB = database.barsQueries

    val markerTitle = mutableStateOf("")
    val markerDesc = mutableStateOf("")
    val addImageProcess = mutableStateOf(false)

    fun addMarker(){
        if (markerTitle.value != "" && markerDesc.value != ""){
            barDB.insertTest(markerTitle.value, markerDesc.value)
        }
    }
}