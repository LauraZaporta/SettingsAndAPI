package cat.itb.m78.exercices.mapsApp.ViewModels

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.db.database

class VMAddMarker : ViewModel(){
    private val barDB = database.barsQueries
    private val imageInsert = mutableStateOf<String?>(null)

    val markerTitle = mutableStateOf("")
    val markerDesc = mutableStateOf("")
    val markerImg = mutableStateOf<Uri?>(null)
    val lat = mutableStateOf(0.0)
    val lon = mutableStateOf(0.0)
    val points = mutableStateOf(0.0F)
    val addImageProcess = mutableStateOf(false)

    fun addMarker(){
        if (markerTitle.value != "" && markerDesc.value != ""){
            // Si la uri és nul·la el que s'insereix a la bd també
            if (markerImg.value != null){
                imageInsert.value = markerImg.value.toString()
            }
            barDB.insert(
                markerTitle.value,
                markerDesc.value,
                lat.value,
                lon.value,
                points.value.toLong(),
                imageInsert.value)
        }
    }
}