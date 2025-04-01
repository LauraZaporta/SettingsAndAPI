package cat.itb.m78.exercices.ProjecteApiBD

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
object Destination{
    @Serializable
    data object ListScreen
    @Serializable
    data class ItemScreen (val id: String)
}

@Composable
fun SorceriesNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destination.ListScreen) {
        composable<Destination.ListScreen> {
            SorceriesListScreen (goToSelectedSorcery = {
                navController.navigate(Destination.ItemScreen(it))
            })
        }
        composable<Destination.ItemScreen> { backStack ->
            val id = backStack.toRoute<Destination.ItemScreen>().id
            SorceryScreen( goToSorceryList = {navController.navigate(Destination.ListScreen)}, id = id )
        }
    }
}