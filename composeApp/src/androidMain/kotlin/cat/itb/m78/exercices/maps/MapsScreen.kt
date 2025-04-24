package cat.itb.m78.exercices.maps

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.AdvancedMarker
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("UnrememberedMutableState")
@Composable
fun MapsScreen(){
    GoogleMap(
        googleMapOptionsFactory = {
            GoogleMapOptions().mapId("DEMO_MAP_ID")
        },
    ) {
        AdvancedMarker(
            state = MarkerState(position = LatLng(-34.0, 151.0)),
            title = "Marker in Sydney"
        )
        AdvancedMarker(
            state = MarkerState(position = LatLng(35.66, 139.6)),
            title = "Marker in Tokyo"
        )
    }
}