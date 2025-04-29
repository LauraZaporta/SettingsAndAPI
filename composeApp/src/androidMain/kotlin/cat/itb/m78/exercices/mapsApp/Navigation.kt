package cat.itb.m78.exercices.mapsApp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import kotlinx.coroutines.launch
import m78exercices.composeapp.generated.resources.Audiowide_Regular
import m78exercices.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun Navigation(){
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
                    onClick = { }
                )
                NavigationDrawerItem(
                    label = { Text(text = "List", fontSize = 3.5.em) },
                    selected = false,
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    onClick = { }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Add marker", fontSize = 3.5.em) },
                    selected = false,
                    icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
                    onClick = { }
                )
            }
        }
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
        }})
        { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AddMarkerScreen()
            }
        }
    }
}