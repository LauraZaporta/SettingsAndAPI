package cat.itb.m78.exercices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cat.itb.m78.exercices.camera.CameraNav
import cat.itb.m78.exercices.maps.MapsScreen

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MapsScreen() }
    }
}

@Preview
@Composable
fun AppPreview() { MapsScreen() }
