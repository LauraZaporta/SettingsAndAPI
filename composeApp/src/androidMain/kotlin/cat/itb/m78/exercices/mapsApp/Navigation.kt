package cat.itb.m78.exercices.mapsApp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import m78exercices.composeapp.generated.resources.Audiowide_Regular
import m78exercices.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Serializable
object Destiny{
    @Serializable
    data object ListScreen
    @Serializable
    data object MapScreen
    @Serializable
    data class AddMarkerScreen(val lat : Double, val lon : Double)
}

@Composable
fun Navigation(){
    val navController = rememberNavController()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("MENU",
                    modifier = Modifier.padding(20.dp).padding(start = 5.dp),
                    fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)),
                    fontSize = 5.em)
                HorizontalDivider( thickness = 3.dp, color = Color.Black)
                NavigationDrawerItem(
                    modifier = Modifier.padding(top = 13.dp),
                    label = { Text(text = "Map", fontSize = 3.5.em) },
                    selected = false,
                    icon = { Icon(Icons.Default.Place, contentDescription = "Map") },
                    onClick = { navController.navigate( Destiny.MapScreen )
                        scope.launch {drawerState.close()}
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = "List", fontSize = 3.5.em) },
                    selected = false,
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    onClick = { navController.navigate( Destiny.ListScreen )
                        scope.launch {drawerState.close()}
                    }
                )
            }
        },
        gesturesEnabled = false
    ) {
        Scaffold (bottomBar = {
            NavigationBar (containerColor = Color.Black){
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        scope.launch {
                            if (drawerState.isClosed) {
                                drawerState.open()
                            } else {
                                drawerState.close()
                            }
                        }
                    },
                    icon = { Icon(imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        tint = Color.White)},
                    label = {Text("Menu",
                        color = Color.White)}
                )
        }},
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()
                .height(120.dp)
                .background(color = Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center)
            {
                Text("BARS APP \uD83C\uDF7A",
                    modifier = Modifier.padding(top = 50.dp),
                    color = Color.White,
                    fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)),
                    fontSize = 5.em)
            }
        })
        { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NavHost(navController = navController, startDestination = Destiny.MapScreen) {
                    composable<Destiny.MapScreen> {
                        MapScreen( goToAddMarkerScreen = { lat, lon ->
                            navController.navigate(Destiny.AddMarkerScreen(lat, lon))
                        } )
                    }
                    composable<Destiny.ListScreen> {
                        MarkerListScreen()
                    }
                    composable<Destiny.AddMarkerScreen> { backStack ->
                        val lat = backStack.toRoute<Destiny.AddMarkerScreen>().lat
                        val lon = backStack.toRoute<Destiny.AddMarkerScreen>().lon
                        AddMarkerScreen(lat, lon) { navController.navigate(Destiny.MapScreen) }
                    }
                }
            }
        }
    }
}