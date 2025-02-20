import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.russhwolf.settings.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountVM : ViewModel(){
    private val settings: Settings = Settings()

    var currentCount: Int = settings.getIntOrNull("key") ?: 0

    fun incrementCount() {
        currentCount += 1
        settings.putInt("key", currentCount)
    }
}

@Composable
fun counterScreen(){
    val countVM = viewModel { CountVM() }
    countVM.incrementCount()

    counterScreenArguments(countVM.currentCount)
}

@Composable
fun counterScreenArguments(numViews : Int){
    Text("You have opened this app $numViews times")
}