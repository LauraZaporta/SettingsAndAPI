package cat.itb.m78.exercices.ProjecteApiBD

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.db.DBViewModel
import coil3.compose.AsyncImage
import m78exercices.composeapp.generated.resources.Audiowide_Regular
import m78exercices.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun SorceryScreen(goToSorceryList: () -> Unit, id : String){
    val sorceriesVM = viewModel { SorceriesVM(id) }

    val sorcery = sorceriesVM.sorcery.value

    if (sorcery != null) {
        SorceryScreenArguments(goToSorceryList, sorcery)
    } else
    {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(
                "Loading...", color = Color.Black,
                fontSize = 3.em,
                fontFamily = FontFamily(Font(Res.font.Audiowide_Regular))
            )
        }
    }
}

@Composable
fun SorceryScreenArguments(goToSorceryList:() -> Unit, sorcery : SingleSorcery){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {
        AsyncImage(
            model = sorcery.data.imgUrl,
            contentDescription = sorcery.data.id
        )
        Spacer(Modifier.height(10.dp))
        Text(sorcery.data.name)
        Text(sorcery.data.desc)
        Text(sorcery.data.effect)
    }
}