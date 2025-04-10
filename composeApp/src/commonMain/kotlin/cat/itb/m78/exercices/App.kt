package cat.itb.m78.exercices


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cat.itb.m78.exercices.API.CountriesArgument
import cat.itb.m78.exercices.API.Jokes
import cat.itb.m78.exercices.API.Countries
import cat.itb.m78.exercices.API.EmbNavigation
import cat.itb.m78.exercices.Examen.StudentsNavigation
import cat.itb.m78.exercices.ProjecteApiBD.SorceriesNavigation
import cat.itb.m78.exercices.db.messages
import cat.itb.m78.exercices.db.messagesCat
import cat.itb.m78.exercices.theme.AppTheme
import counterScreen
import org.jetbrains.compose.reload.DevelopmentEntryPoint
import testDB

@Composable
internal fun App() = AppTheme {
    StudentsNavigation()
}
