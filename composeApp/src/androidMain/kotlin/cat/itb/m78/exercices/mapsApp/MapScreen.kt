package cat.itb.m78.exercices.mapsApp

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import com.google.android.gms.maps.GoogleMapOptions
import com.google.maps.android.compose.GoogleMap

@SuppressLint("UnrememberedMutableState")
@Composable
fun MapScreen(){
    GoogleMap(
        googleMapOptionsFactory = {
            GoogleMapOptions().mapId("DEMO_MAP_ID")
        },
    ) {
        // Makers here
    }
}