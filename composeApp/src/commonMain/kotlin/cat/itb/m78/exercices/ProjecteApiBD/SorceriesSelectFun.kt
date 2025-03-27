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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.db.DBViewModel
import coil3.compose.AsyncImage

@Composable
fun SorceryScreen(id: String){

}

@Composable
fun SorceryScreenArguments(sorcery : Sorcery){
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