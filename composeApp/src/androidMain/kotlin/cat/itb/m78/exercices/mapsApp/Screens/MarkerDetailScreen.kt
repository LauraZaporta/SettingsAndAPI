package cat.itb.m78.exercices.mapsApp.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.mapsApp.DTOs.CustomMarkerWithImg
import cat.itb.m78.exercices.mapsApp.ViewModels.VMDetail
import coil3.compose.AsyncImage
import m78exercices.composeapp.generated.resources.Audiowide_Regular
import m78exercices.composeapp.generated.resources.Res

@Composable
fun MarkerDetailScreen(lat : Double, lon : Double, goToMapScreen : () -> Unit){
    val detailVM = viewModel { VMDetail(lat, lon) }

    MarkerDetailScreenArguments(detailVM.marker.value, detailVM::deleteBar, goToMapScreen)
}

@Composable
fun MarkerDetailScreenArguments(marker: CustomMarkerWithImg?, deleteByLatLng : (Double, Double) -> Unit,
                                goToMapScreen: () -> Unit) {
    if (marker == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No bar found at this location.",
                fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)),
                fontSize = 5.em
            )
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .padding(bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color.Black)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                marker.image?.let { imageUri ->
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Bar Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = marker.title,
                    fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)),
                    fontSize = 6.em,
                    color = Color.White
                )
                Text(
                    text = "Score: ${marker.points}/10",
                    fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)),
                    fontSize = 4.em,
                    color = Color.White
                )
                marker.description?.let {
                    Text(
                        text = "Description: $it",
                        fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)),
                        fontSize = 4.em,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Text(
                    text = "Lat: ${marker.latLng.latitude}",
                    fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)),
                    fontSize = 4.em,
                    color = Color.White
                )
                Text(
                    text = "Lng: ${marker.latLng.longitude}",
                    fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)),
                    fontSize = 4.em,
                    color = Color.White
                )
                Button(
                    modifier = Modifier.height(60.dp).width(175.dp),
                    onClick = { deleteByLatLng(marker.latLng.latitude, marker.latLng.longitude)
                              goToMapScreen() },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White)
                ) {
                    Text("X Delete X",
                        color = Color.Black,
                        fontSize = 4.em,
                        fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)))
                }
            }
        }
    }
}