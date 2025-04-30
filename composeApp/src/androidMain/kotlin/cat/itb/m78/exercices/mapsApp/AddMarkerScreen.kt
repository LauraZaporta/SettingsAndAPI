package cat.itb.m78.exercices.mapsApp

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import m78exercices.composeapp.generated.resources.Audiowide_Regular
import m78exercices.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun CameraPermission() {
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )
    if (cameraPermissionState.status.isGranted) {
        Text("Camera permission Granted")
    } else {
        Column {
            val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                "The camera is important for this app. Please grant the permission."
            } else {
                "Camera permission required for this feature to be available. " +
                        "Please grant the permission"
            }
            Text(textToShow)
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("Request permission")
            }
        }
    }
}

@Composable
fun AddMarkerScreen(){
    val addMarkerVM = viewModel { VMAddMarker() }

    AddMarkerScreenArguments(addMarkerVM.markerTitle, addMarkerVM.markerDesc,
        addMarkerVM.addImageProcess)
}

@Composable
fun AddMarkerScreenArguments(title : MutableState<String>, description : MutableState<String>,
                             addImageProcess : MutableState<Boolean>){

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

        // Imatge
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
                Button(
                    modifier = Modifier.height(40.dp).width(120.dp).padding(3.dp),
                    onClick = { },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black)
                ) {
                    Text("Photo",
                        color = Color.White,
                        fontSize = 3.em,
                        fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)))
                }
                Button(
                    modifier = Modifier.height(40.dp).width(120.dp).padding(3.dp),
                    onClick = { },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black)
                ) {
                    Text("Search",
                        color = Color.White,
                        fontSize = 3.em,
                        fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)))
                }
                Button(
                    modifier = Modifier.height(40.dp).width(120.dp).padding(3.dp),
                    onClick = { addImageProcess.value = false},
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black)
                ) {
                    Text("Cancel",
                        color = Color.White,
                        fontSize = 3.em,
                        fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)))
                }
            }
        }

        Spacer(Modifier.height(30.dp))
        Button(
            modifier = Modifier.height(60.dp).width(100.dp),
            onClick = { },
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