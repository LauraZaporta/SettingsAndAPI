package cat.itb.m78.exercices.mapsApp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

data class CustomMarker(
    val latLng : LatLng,
    val title : String,
    val description : String,
    val points : Int
)

class VMMaps : ViewModel(){
    val markers = mutableStateOf<List<CustomMarker>>(emptyList())
    var clickedMarker = mutableStateOf<LatLng?>(null)
}