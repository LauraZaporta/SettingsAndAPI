package cat.itb.m78.exercices.Examen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Serializable
object Destiny{
    @Serializable
    data object ListScreen
    @Serializable
    data object ListScreenExtension
    @Serializable
    data object SkipScreen
}

@Composable
fun StudentsNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destiny.ListScreen) {
        composable<Destiny.ListScreen> {
            StudentsListScreen (goToSkipScreen =  {navController.navigate(Destiny.SkipScreen)},
                goToExtensionScreen = {navController.navigate(Destiny.ListScreenExtension)})
        }
        composable<Destiny.SkipScreen> {
            SkipScreen(goToStudentsList = {navController.navigate(Destiny.ListScreen)})
        }
        composable<Destiny.ListScreenExtension> {
            StudentsListScreenExtension (goToSkipScreen = {navController.navigate(Destiny.SkipScreen)},
                goToListScreen = {navController.navigate(Destiny.ListScreen)})
        }
    }
}