package cat.itb.m78.exercices.camera

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cat.itb.m78.exercices.ProjecteApiBD.Destination
import cat.itb.m78.exercices.ProjecteApiBD.SorceriesListScreen
import cat.itb.m78.exercices.ProjecteApiBD.SorceryScreen
import kotlinx.serialization.Serializable

@Serializable
object Destiny{
    @Serializable
    data object PermissionScreen
    @Serializable
    data object CameraScreen
}

@Composable
fun CameraNav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destiny.PermissionScreen) {
        composable<Destiny.PermissionScreen> {
            FeatureThatRequiresCameraPermission (goToCameraScreen = {
                navController.navigate(Destiny.CameraScreen) })
        }
        composable<Destiny.CameraScreen> {
            CameraScreen()
        }
    }
}