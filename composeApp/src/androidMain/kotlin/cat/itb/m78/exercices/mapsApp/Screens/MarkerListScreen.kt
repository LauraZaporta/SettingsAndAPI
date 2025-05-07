package cat.itb.m78.exercices.mapsApp.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.mapsApp.DTOs.CustomMarker
import cat.itb.m78.exercices.mapsApp.ViewModels.VMMarkersList
import com.google.android.gms.maps.model.LatLng
import m78exercices.composeapp.generated.resources.Audiowide_Regular
import m78exercices.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun MarkerListScreen(goToDetailScreen: (LatLng) -> Unit){
    val listVM = viewModel { VMMarkersList() }

    MarkerListScreenArguments(listVM.markers.value, goToDetailScreen)
}

@Composable
fun MarkerListScreenArguments(markers: List<CustomMarker>, goToDetailScreen : (LatLng) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (markers.isEmpty()) {
            Text(
                "No bars registered yet",
                modifier = Modifier.padding(top = 50.dp),
                fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)),
                fontSize = 5.em
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(15.dp)
                    .padding(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                items(markers.chunked(2)) { rowMarkers ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        rowMarkers.forEach { marker ->
                            Card(
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(100.dp)
                                    .clickable(
                                        enabled = true,
                                        onClickLabel = "Clickable card",
                                        // PK -> LatLng de cada marker
                                        onClick = { goToDetailScreen(marker.latLng) }
                                    )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Black)
                                        .padding(8.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        "Bar: ${marker.title}",
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        fontFamily = FontFamily(Font(Res.font.Audiowide_Regular))
                                    )
                                    Text(
                                        "Score: ${marker.points}/10",
                                        color = Color.White,
                                        fontFamily = FontFamily(Font(Res.font.Audiowide_Regular))
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}