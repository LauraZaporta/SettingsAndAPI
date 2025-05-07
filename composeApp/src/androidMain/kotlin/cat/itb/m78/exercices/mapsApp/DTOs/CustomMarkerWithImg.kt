package cat.itb.m78.exercices.mapsApp.DTOs

import com.google.android.gms.maps.model.LatLng

data class CustomMarkerWithImg(
    val latLng : LatLng,
    val title : String,
    val description : String?,
    val points : Long,
    val image : String?
)