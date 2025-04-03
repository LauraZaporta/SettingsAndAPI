package cat.itb.m78.exercices.ProjecteApiBD

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.resources.Font
import m78exercices.composeapp.generated.resources.Res
import m78exercices.composeapp.generated.resources.Audiowide_Regular
import coil3.compose.AsyncImage

@Composable
fun SorceriesListScreen(goToSelectedSorcery: (String) -> Unit){
    val sorceriesVM = viewModel { SorceriesVMList() }

    val listSorceries = sorceriesVM.sorceriesList.value
    val search = sorceriesVM.searchQuery
    val loading = sorceriesVM.loading.value
    val brush = Brush.horizontalGradient(listOf(Color(0XFF192340), Color(0XFF1A2342),
        Color(0XFF3D418B), Color(0XFF4A70AF), Color(0XFF929EC6)))

    if (loading){
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text("Loading...", color = Color.Black,
                fontSize = 3.em,
                fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)))
        }
    }
    else {
        SorceriesListScreenArguments(listSorceries, search, goToSelectedSorcery, brush, sorceriesVM::listIsAll,
            sorceriesVM::listIsFavs)
    }
}

@Composable
fun SorceriesListScreenArguments(listSorceries : List<SorceryData>, search : MutableState<String>,
                                 goToSelectedSorcery:(String) -> Unit, cardBrush: Brush,
                                 listIsAll :() -> Unit, listIsFavs :() -> Unit)
{
    Scaffold (bottomBar = {
        NavigationBar (containerColor = Color(0XFF3D418B)){
            NavigationBarItem(
                selected = false,
                onClick = listIsAll,
                icon = { Icon(imageVector = Icons.Default.Home,
                    contentDescription = null,
                    tint = Color.White)},
                label = {Text("All",
                    color = Color.White,
                    fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)))}
            )
            NavigationBarItem(
                selected = false,
                onClick = listIsFavs,
                icon = { Icon(imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.White)},
                label = {Text("Favourites",
                    color = Color.White,
                    fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)))}
            )
        }
    })
    {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally)
        {
            OutlinedTextField(
                modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
                value = search.value,
                label = { Text("Search your sorcery") },
                onValueChange = { search.value = it }
            )
            LazyColumn(modifier = Modifier.padding(15.dp).padding(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),)
            {
                val filteredSorceries = listSorceries.filter {it.name.contains(search.value, ignoreCase = true)}
                if (filteredSorceries.isEmpty()){
                    item{
                        Text("No sorcery found", fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)))
                    }
                }
                items(filteredSorceries) {sorcery ->
                    Card(modifier = Modifier.width(500.dp).height(100.dp)
                        .clickable(
                            enabled = true,
                            onClickLabel = "Clickable card",
                            onClick = {
                                goToSelectedSorcery(sorcery.id)
                            }))
                    {
                        Row(modifier = Modifier.fillMaxWidth().background(cardBrush), horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically)
                        {
                            Text(sorcery.name,
                                color = Color.White,
                                fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)))
                            AsyncImage(
                                model = sorcery.imgUrl,
                                contentDescription = sorcery.id
                            )
                        }
                    }
                }
            }
        }
    }
}