import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.API.EmbVM
import cat.itb.m78.exercices.db.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import m78exercices.composeapp.generated.resources.Res

data class testObject(
    val id : Long,
    val text : String
)

class DBViewModel: ViewModel() {
    private val table = database.testQueries

    fun insert(text : String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                table.insert(text)
            }
        }
    }
    fun selectAll(): List<testObject> {
        return table.selectAll().executeAsList().map { row ->
            testObject(
                id = row.id,
                text = row.text
            )
        }
    }
    fun selectById(num : Long): testObject?{
        return table.find(num).executeAsOneOrNull()?.let { row ->
            testObject(
                id = row.id,
                text = row.text
            )
        }
    }
    fun deleteAll(){
        table.deleteAll()
    }
}

@Composable
fun testDB(){
    val DBVM = viewModel { DBViewModel() }

    DBVM.insert("Awanabumbambambachapariyo")
    DBVM.insert("Adiósseñorabonitaadióssashalacatrina")

    val listSelectAll = DBVM.selectAll()
    val selectOne = DBVM.selectById(4)

    Column() {
        Text(selectOne?.text ?: "LACATRINA")

        LazyColumn(modifier = Modifier.padding(15.dp), verticalArrangement = Arrangement.spacedBy(15.dp)) {
            items(listSelectAll) {test ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(end = 15.dp).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(Modifier.height(3.dp))
                        Text(test.id.toString())
                        Spacer(Modifier.height(3.dp))
                        Text(test.text)
                        Spacer(Modifier.height(3.dp))
                    }
                }
            }
        }
    }
    DBVM.deleteAll()
}