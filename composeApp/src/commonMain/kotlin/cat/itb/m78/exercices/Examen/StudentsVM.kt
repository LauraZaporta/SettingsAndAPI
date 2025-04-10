package cat.itb.m78.exercices.Examen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.itb.m78.exercices.ProjecteApiBD.SorceryData
import cat.itb.m78.exercices.db.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock.System.now

class StudentsVM: ViewModel() {
    private val studentsDB = database.studentsDBQueries
    val studentList = mutableStateOf<List<Student>>(emptyList())
    val loadingAPI = mutableStateOf(true)

    fun registerSkippedClass(id : Int){
        studentsDB.insertSkip(id.toLong(), now().toString())
    }

    fun countSkips(id : Int) : Int{
        var count = 0
        for (skip in studentsDB.selectSkips().executeAsList()){
            if (id.toLong() == skip.ID){
                count++
            }
        }
        return count
    }

    init {
        viewModelScope.launch(Dispatchers.Default) {
            studentList.value = StudentsAPI().list()
            loadingAPI.value = false
        }
    }
}