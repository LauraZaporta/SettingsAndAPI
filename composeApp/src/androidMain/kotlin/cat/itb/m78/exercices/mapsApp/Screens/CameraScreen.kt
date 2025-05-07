package cat.itb.m78.exercices.mapsApp.Screens

import android.content.Context
import android.net.Uri
import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.SurfaceRequest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.mapsApp.ViewModels.VMCamera
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import m78exercices.composeapp.generated.resources.Audiowide_Regular
import m78exercices.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

const val PHOTO_URI_KEY = "photo_uri"

@Composable
fun CameraScreen(navController: NavController) {
    val cameraVM = viewModel { VMCamera() }

    CameraArgumentsScreen(cameraVM.surferRequest.value, cameraVM.savedPhotoUri.value, navController,
        cameraVM::bindToCamera, cameraVM::takePhoto)
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraArgumentsScreen(surfaceRequest: SurfaceRequest?, savedUri: Uri?, navController: NavController,
                          bindToCamera: suspend (Context, LifecycleOwner) -> Unit, takePhoto: (Context) -> Unit)
{
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        bindToCamera(context, lifecycleOwner)
    }

    LaunchedEffect(savedUri) {
        if (savedUri != null) {
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set(PHOTO_URI_KEY, savedUri.toString())
            navController.popBackStack()
        }
    }

    if (cameraPermissionState.status.isGranted) {
        surfaceRequest?.let { request ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CameraXViewfinder(
                    surfaceRequest = request,
                    modifier = Modifier.fillMaxSize()
                )
                Button(
                    modifier = Modifier.height(50.dp).width(150.dp).padding(5.dp),
                    onClick = { takePhoto(context) },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black)
                ) {
                    Text("Take photo",
                        color = Color.White,
                        fontSize = 3.em,
                        fontFamily = FontFamily(Font(Res.font.Audiowide_Regular))
                    )
                }
            }
        }
    }
    else {
        val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
            "The camera is important for this app. Please grant the permission."
        } else {
            "Camera permission required for this feature. Please grant it to continue."
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black, contentColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = textToShow,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Button(
                        onClick = { cameraPermissionState.launchPermissionRequest() },
                        modifier = Modifier.fillMaxWidth(0.6f).background(Color.White),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text("Request permission", color = Color.Black)
                    }
                }
            }
        }
    }
}