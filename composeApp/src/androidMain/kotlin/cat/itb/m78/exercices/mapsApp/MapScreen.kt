package cat.itb.m78.exercices.mapsApp

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.GoogleMapOptions
import com.google.maps.android.compose.AdvancedMarker
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerState
import m78exercices.composeapp.generated.resources.Audiowide_Regular
import m78exercices.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@SuppressLint("UnrememberedMutableState")
@Composable
fun MapScreen(goToAddMarkerScreen : (Double, Double) -> Unit) {
    val mapsVM = viewModel { VMMaps() }

    GoogleMap(
        googleMapOptionsFactory = {
            GoogleMapOptions().mapId("DEMO_MAP_ID")
        },
        onMapClick = { latLng ->
            // Add a marker when the user clicks anywhere on the map
            mapsVM.clickedMarker.value = latLng
        },
    ) {
        // Add markers
        if (mapsVM.clickedMarker.value != null){
            AdvancedMarker(
                state = MarkerState(position = LatLng(
                    mapsVM.clickedMarker.value!!.latitude,
                    mapsVM.clickedMarker.value!!.longitude)
                ),
                title = "New Marker"
            )
        }
        mapsVM.markers.value.forEach { marker ->
            AdvancedMarker(
                state = MarkerState(position = LatLng(marker.latLng.latitude, marker.latLng.longitude)),
                title = marker.title,
                contentDescription = marker.description
            )
        }
    }
    if (mapsVM.clickedMarker.value != null){
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                border = BorderStroke(2.dp, Color.Black),
                modifier = Modifier
                    .size(width = 350.dp, height = 150.dp)
                    .padding(10.dp)
            )  {
                Column(modifier = Modifier.width(350.dp)
                    .height(150.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Text("Add new marker",
                        modifier = Modifier.padding(10.dp).padding(bottom = 10.dp),
                        fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)),
                        fontSize = 5.em)
                    Row(horizontalArrangement = Arrangement.Center){
                        Button(
                            modifier = Modifier.height(40.dp).width(120.dp).padding(3.dp),
                            onClick = { goToAddMarkerScreen(
                                mapsVM.clickedMarker.value!!.latitude,
                                mapsVM.clickedMarker.value!!.longitude) },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black)
                        ) {
                            Text("Add",
                                color = Color.White,
                                fontSize = 3.em,
                                fontFamily = FontFamily(Font(Res.font.Audiowide_Regular))
                            )
                        }
                        Button(
                            modifier = Modifier.height(40.dp).width(120.dp).padding(3.dp),
                            onClick = { mapsVM.clickedMarker.value = null },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black)
                        ) {
                            Text("Cancel",
                                color = Color.White,
                                fontSize = 3.em,
                                fontFamily = FontFamily(Font(Res.font.Audiowide_Regular))
                            )
                        }
                    }
                }
            }
        }
    }
}