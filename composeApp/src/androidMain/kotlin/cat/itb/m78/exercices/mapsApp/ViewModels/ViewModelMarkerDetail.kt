package cat.itb.m78.exercices.mapsApp.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.db.database
import com.google.android.gms.maps.model.LatLng
import cat.itb.m78.exercices.mapsApp.DTOs.CustomMarkerWithImg

class VMDetail(lat: Double, lon: Double) : ViewModel() {
    private val barDB = database.barsQueries

    val marker = mutableStateOf(
        barDB.selectByLatLng(lat, lon).executeAsOneOrNull()?.let { bar ->
            CustomMarkerWithImg(
                latLng = LatLng(bar.latitude, bar.longitude),
                title = bar.title,
                description = bar.description,
                points = bar.points,
                image = bar.image
            )
        }
    )
}