package cat.itb.m78.exercices.Examen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import m78exercices.composeapp.generated.resources.Audiowide_Regular
import m78exercices.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun SkipScreen(goToStudentsList: () -> Unit){
    val skipsVM = viewModel { SkipsVM() }
    SkipScreenArguments(goToStudentsList, skipsVM.skipsList.value)
}

@Composable
fun SkipScreenArguments(goToStudentsList:() -> Unit, skipedList : List<skipedData>){
    Column(modifier = Modifier.fillMaxSize().padding(20.dp).background(Color.Blue), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(
            "SKIPED CLASSES", fontSize = 2.em, color = Color.White, modifier = Modifier.padding(top = 30.dp),
            fontFamily = FontFamily(Font(Res.font.Audiowide_Regular))
        )
        Spacer(Modifier.height(15.dp))
        Button(
            onClick = { goToStudentsList() },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White)
        ) {
            Text("Return to student list",
                color = Color.Blue,
                fontFamily = FontFamily(Font(Res.font.Audiowide_Regular)))
        }
        Spacer(Modifier.height(15.dp))
        LazyColumn(
            modifier = Modifier.padding(15.dp).padding(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
        )
        {
            items(skipedList) { skip ->
                Row(modifier = Modifier.width(500.dp).height(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center)
                {
                    Text(skip.studentId.toString(), color = Color.White)
                    Spacer(Modifier.width(10.dp))
                    Text(skip.studentName, color = Color.White)
                    Spacer(Modifier.width(10.dp))
                    Text(skip.date, color = Color.White)
                }
            }
        }
    }
}