package cat.itb.m78.exercices.mapsApp.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.db.database
import cat.itb.m78.exercices.mapsApp.DTOs.CustomMarker
import com.google.android.gms.maps.model.LatLng

class VMMarkersList : ViewModel() {
    private val barDB = database.barsQueries

    val search = mutableStateOf("")
    val markers = mutableStateOf(
        barDB.selectAll().executeAsList().map { bar ->
            CustomMarker(
                latLng = LatLng(bar.latitude, bar.longitude),  // Map latitude and longitude to LatLng
                title = bar.title,                           // Map title
                description = bar.description,               // Map description
                points = bar.points                          // Map points
            )
        }
    )

    fun deleteBar(lat: Double, lon: Double){
        barDB.deleteByLatLng(lat, lon)
        markers.value = barDB.selectAll().executeAsList().map { bar ->
            CustomMarker(
                latLng = LatLng(bar.latitude, bar.longitude),
                title = bar.title,
                description = bar.description,
                points = bar.points
            )
        }
    }
}