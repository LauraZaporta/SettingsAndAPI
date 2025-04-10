package cat.itb.m78.exercices.Examen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.itb.m78.exercices.db.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock.System.now

data class skipedData(
    val studentId: Long,
    val studentName: String,
    val date: String
)

class SkipsVM: ViewModel() {
    private val studentsDB = database.studentsDBQueries
    private val studentList = mutableStateOf<List<Student>>(emptyList())
    val skipsList = mutableStateOf<List<skipedData>>(emptyList())

    private fun updateSkipsList(){
        val skips = mutableListOf<skipedData>()
        var skip : skipedData
        val studentName = mutableStateOf("")
        for (skipInList in studentsDB.selectSkips().executeAsList()){
            for (student in studentList.value){
                if (skipInList.ID == student.id.toLong()){
                    studentName.value = student.name
                }
            }
            skip = skipedData(skipInList.ID, studentName.value, skipInList.DATE)
            skips.add(skip)
        }
        skipsList.value = skips
    }

    init {
        viewModelScope.launch(Dispatchers.Default) {
            studentList.value = StudentsAPI().list()
            updateSkipsList()
        }
    }
}