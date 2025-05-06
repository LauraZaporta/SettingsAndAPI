package cat.itb.m78.exercices.mapsApp.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.db.database
import com.google.android.gms.maps.model.LatLng

data class CustomMarker(
    val latLng : LatLng,
    val title : String?,
    val description : String?,
    val points : Long?
)

class VMMaps : ViewModel(){
    private val barDB = database.barsQueries

    val markers = mutableStateOf<List<CustomMarker>>(
        barDB.selectAll().executeAsList().map { bar ->
            CustomMarker(
                latLng = LatLng(bar.latitude, bar.longitude),  // Map latitude and longitude to LatLng
                title = bar.title,                           // Map title
                description = bar.description,               // Map description
                points = bar.points                          // Map points
            )
        }
    )
    var clickedMarker = mutableStateOf<LatLng?>(null)
}