package cat.itb.m78.exercices.ProjecteApiBD

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import m78exercices.composeapp.generated.resources.wallpaper
import org.jetbrains.compose.resources.painterResource
import coil3.compose.AsyncImage
import m78exercices.composeapp.generated.resources.Audiowide_Regular
import m78exercices.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Font

@Composable
fun SorceryScreen(goToSorceryList: () -> Unit, id : String){
    val sorceriesVM = viewModel { SorceriesVM(id) }

    val sorcery = sorceriesVM.sorcery.value
    val image = Res.drawable.wallpaper

    if (sorcery != null) {
        SorceryScreenArguments(goToSorceryList, sorcery, image)
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
fun SorceryScreenArguments(goToSorceryList:() -> Unit, sorcery : SingleSorcery, background : DrawableResource){
    Box(modifier = Modifier.fillMaxSize()){
        Image(painter = painterResource(background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize())

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            Text(
                sorcery.data.name,
                fontSize = 2.5.em,
                fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)),
                color = Color.White)
            Spacer(Modifier.height(20.dp))
            AsyncImage(
                modifier = Modifier.size(250.dp),
                model = sorcery.data.imgUrl,
                contentDescription = sorcery.data.id
            )
            Spacer(Modifier.height(10.dp))
            Text(
                sorcery.data.desc,
                modifier = Modifier.padding(horizontal = 30.dp),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)),
                color = Color.White)
            Spacer(Modifier.height(15.dp))
            Text(
                sorcery.data.effect,
                modifier = Modifier.padding(horizontal = 30.dp),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)),
                color = Color.White)
            Spacer(Modifier.height(30.dp))
            Button(
                onClick = { goToSorceryList() },
                shape = CutCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White)
            ) {
                Text("Return to list",
                    color = Color.Black,
                    fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)))
            }
        }
    }
}