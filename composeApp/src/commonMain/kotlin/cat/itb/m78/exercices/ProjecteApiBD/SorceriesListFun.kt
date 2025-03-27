package cat.itb.m78.exercices.ProjecteApiBD

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.db.DBViewModel
import coil3.compose.AsyncImage

@Composable
fun SorceriesListScreen(goToSelectedSorcery: (String) -> Unit){
    val sorceriesVM = viewModel { SorceriesVMList() }

    SorceriesListScreenArguments(sorceriesVM.sorceriesList.value, )
}

@Composable
fun SorceriesListScreenArguments(listSorceries : List<Sorcery>, search : MutableState<String>,
                              goToSelectedSorcery:(String) -> Unit){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally)
    {
        TextField(
            value = search.value,
            label = { Text("Search your sorcery") },
            onValueChange = { search.value = it }
        )
        LazyColumn(modifier = Modifier.padding(15.dp), verticalArrangement = Arrangement.spacedBy(15.dp))
        {
            items(listSorceries) {sorcery ->
                Card(modifier = Modifier.fillMaxWidth().height(100.dp)
                    .clickable(
                        enabled = true,
                        onClickLabel = "Clickable card",
                        onClick = {goToSelectedSorcery(sorcery.data.id)}))
                {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround)
                    {
                        Text(sorcery.data.name)
                        AsyncImage(
                            model = sorcery.data.imgUrl,
                            contentDescription = sorcery.data.id
                        )
                    }
                }
            }
        }
    }
}