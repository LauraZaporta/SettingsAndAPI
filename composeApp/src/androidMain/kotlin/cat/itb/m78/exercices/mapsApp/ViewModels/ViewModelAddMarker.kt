package cat.itb.m78.exercices.mapsApp.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.db.database

class VMAddMarker : ViewModel(){
    private val barDB = database.barsQueries

    val markerTitle = mutableStateOf("")
    val markerDesc = mutableStateOf("")
    val lat = mutableStateOf(0.0)
    val lon = mutableStateOf(0.0)
    val points = mutableStateOf(0.0F)
    val addImageProcess = mutableStateOf(false)

    fun addMarker(){
        if (markerTitle.value != "" && markerDesc.value != ""){
            barDB.insert(
                markerTitle.value,
                markerDesc.value,
                lat.value,
                lon.value,
                points.value.toLong(),
                null)
        }
    }
}