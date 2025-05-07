package cat.itb.m78.exercices.mapsApp.Screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.mapsApp.ViewModels.VMAddMarker
import coil3.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import m78exercices.composeapp.generated.resources.Audiowide_Regular
import m78exercices.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
private fun GeneratePhotoButton(function : () -> Unit, text : String){
    Button(
        modifier = Modifier.height(40.dp).width(120.dp).padding(3.dp),
        onClick = { function() },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black)
    ) {
        Text(text,
            color = Color.White,
            fontSize = 3.em,
            fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)))
    }
}

@Composable
fun AddMarkerScreen(lat : Double, lon : Double, goToMapScreen : () -> Unit, imageUri: Uri?,
                    goToCameraScreen : () -> Unit)
{
    val addMarkerVM = viewModel { VMAddMarker() }

    addMarkerVM.lat.value = lat
    addMarkerVM.lon.value = lon

    if (imageUri != null) {
        addMarkerVM.markerImg.value = imageUri
    }

    AddMarkerScreenArguments(addMarkerVM.markerTitle, addMarkerVM.markerDesc, addMarkerVM.markerImg,
        addMarkerVM.addImageProcess, addMarkerVM.points, addMarkerVM :: addMarker, goToMapScreen,
        goToCameraScreen)
}

@Composable
fun AddMarkerScreenArguments(title : MutableState<String>, description : MutableState<String>,
                             image : MutableState<Uri?>, addImageProcess : MutableState<Boolean>,
                             points : MutableState<Float>, addMarker: () -> Unit, goToMapScreen: () -> Unit,
                             goToCameraScreen: () -> Unit)
{
    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            image.value = uri
        }
    }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text("Add marker",
            fontSize = 7.em,
            fontFamily = FontFamily(Font(Res.font.Audiowide_Regular))
        )
        Spacer(Modifier.height(25.dp))
        OutlinedTextField(
            modifier = Modifier.width(300.dp),
            value = title.value,
            onValueChange = { title.value = it },
            label = { Text("Title") },
            singleLine = true
        )
        Spacer(Modifier.height(15.dp))
        OutlinedTextField(
            modifier = Modifier.height(100.dp).width(300.dp),
            value = description.value,
            onValueChange = { description.value = it },
            label = { Text("Description") },
            maxLines = 4
        )
        Spacer(Modifier.height(30.dp))

        // Puntuació del bar
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
            Text("BAR SCORE     0",
                fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)),
                modifier = Modifier.padding(top = 15.dp))
            Spacer(Modifier.width(20.dp))
            Row(modifier = Modifier.width(150.dp).padding(bottom = 15.dp)){
                Slider(
                    value = points.value,
                    onValueChange = { points.value = it },
                    colors = SliderDefaults.colors(
                        thumbColor = Color.Black,
                        activeTrackColor = Color.Black,
                        inactiveTrackColor = Color.White,
                    ),
                    steps = 10,
                    valueRange = 0f..10f
                )
            }
            Spacer(Modifier.width(20.dp))
            Text("10",
                fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)),
                modifier = Modifier.padding(top = 15.dp))
        }
        Spacer(Modifier.height(15.dp))

        // Imatge
        if (image.value != null) {
            AsyncImage(
                model = image.value,
                contentDescription = "Captured image",
                modifier = Modifier
                    .width(200.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(10.dp))
        } else {
            Column(modifier = Modifier
                .border(BorderStroke(1.dp, SolidColor(Color.Black)),
                    shape = RoundedCornerShape(7.dp))
                .width(200.dp)
                .height(120.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                IconButton( onClick = {addImageProcess.value = true} )
                {
                    Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "Camera",
                        modifier = Modifier.size(100.dp))
                }
                Spacer(Modifier.height(5.dp))
                Text("Add image", fontSize = 3.5.em)
            }
            if (addImageProcess.value){
                Spacer(Modifier.height(10.dp))
                Row(){
                    GeneratePhotoButton({ goToCameraScreen() }, "Photo")
                    GeneratePhotoButton({ galleryLauncher.launch("image/*") }, "Gallery")
                    GeneratePhotoButton({addImageProcess.value = false}, "Cancel")
                }
            }
        }

        Spacer(Modifier.height(30.dp))
        Button(
            modifier = Modifier.height(60.dp).width(100.dp),
            onClick = { addMarker()
                      goToMapScreen()},
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black)
        ) {
            Text("Add",
                color = Color.White,
                fontSize = 4.em,
                fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)))
        }
    }
}