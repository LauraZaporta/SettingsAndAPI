package cat.itb.m78.exercices.mapsApp.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.db.database
import cat.itb.m78.exercices.mapsApp.DTOs.CustomMarker
import com.google.android.gms.maps.model.LatLng

class VMMaps : ViewModel(){
    private val barDB = database.barsQueries

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
    var clickedMarker = mutableStateOf<LatLng?>(null)
}