package cat.itb.m78.exercices.Examen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import m78exercices.composeapp.generated.resources.Audiowide_Regular
import m78exercices.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun StudentsListScreen(goToSkipScreen: () -> Unit, goToExtensionScreen: () -> Unit){
    val studentsVM = viewModel { StudentsVM() }

    if (studentsVM.loadingAPI.value){
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text("Loading...", color = Color.Black,
                fontSize = 3.em,
                fontFamily = FontFamily(Font(Res.font.Audiowide_Regular))
            )
        }
    }
    else {
        StudentsListScreenArguments(goToSkipScreen, { studentsVM.registerSkippedClass(it) }, goToExtensionScreen,
            studentsVM.studentList.value)
    }
}

@Composable
fun StudentsListScreenArguments(goToSkipScreen: () -> Unit, registerSkipedClass: (Int) -> Unit,
                                goToExtensionScreen: () -> Unit, studentList : List<Student>){
    Scaffold ( bottomBar = {
        NavigationBar (containerColor = Color.Blue){
            NavigationBarItem(
                selected = false,
                onClick = {
                    goToSkipScreen()
                },
                icon = { Icon(imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White)},
                label = {Text("Skipped classes",
                    color = Color.White)}
            )
            NavigationBarItem(
                selected = false,
                onClick = {
                    goToExtensionScreen()
                },
                icon = { Icon(imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Color.White)},
                label = {Text("Students and skips",
                    color = Color.White)}
            )
        }
    })
    {
        Column(modifier = Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text("STUDENTS", fontSize = 2.5.em, color = Color.Blue,
                fontFamily = FontFamily(Font(Res.font.Audiowide_Regular))
            )
            Spacer(Modifier.height(15.dp))
            LazyColumn(modifier = Modifier.padding(15.dp).padding(bottom = 70.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),)
            {
                items(studentList) {student ->
                    Card(modifier = Modifier.width(600.dp).height(175.dp)
                        .clickable(
                            enabled = true,
                            onClickLabel = "Clickable card",
                            onClick = {
                                registerSkipedClass(student.id)
                            }))
                    {
                        Row(modifier = Modifier.fillMaxSize().background(Color.Blue).padding(20.dp)
                            .padding(horizontal = 40.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically)
                        {
                            Column(){
                                Text(student.name + " " + student.surnames, color = Color.White)
                                Spacer(Modifier.height(5.dp))
                                Text(student.email, color = Color.White)
                            }
                            AsyncImage(
                                modifier = Modifier.height(100.dp).width(100.dp)
                                    .border(4.dp, Color.White, RoundedCornerShape(20.dp))
                                    .clip(RoundedCornerShape(20.dp)),
                                model = student.photo,
                                contentDescription = "Student photo"
                            )
                        }
                    }
                }
            }
        }
    }
}