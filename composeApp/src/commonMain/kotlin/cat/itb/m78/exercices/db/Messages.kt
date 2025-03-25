package cat.itb.m78.exercices.db

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.API.Emb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import m78exercices.composeapp.generated.resources.Res

class DBViewModel: ViewModel() {
    private val messages = database.messagesQueries
    val listMessages = mutableStateOf<List<String>>(selectAll())
    val newMessage = mutableStateOf<String>("")

    fun insert() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                messages.insert(newMessage.value)
                listMessages.value = selectAll()
            }
        }
    }
    private fun selectAll() : MutableList<String> {
        return messages.selectAll().executeAsList().toMutableList()
    }
    fun deleteAll() {
        messages.deleteAll()
        listMessages.value = selectAll()
    }
}

@Composable
fun messages(){
    val DBVM = viewModel { DBViewModel() }
    messagesArguments(DBVM.listMessages.value, DBVM.newMessage, DBVM::insert, DBVM::deleteAll)
}

@Composable
fun messagesArguments(listMessages: List<String>, message: MutableState<String>,
                      addMessageToDB:() -> Unit, deleteAllMessages:() -> Unit){

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {
        TextField(
            value = message.value,
            label = { Text("Message") },
            onValueChange = { message.value = it }
        )
        Spacer(Modifier.height(10.dp))
        Button( onClick = addMessageToDB ){
            Text("Add message")
        }
        Button( onClick = deleteAllMessages ){
            Text("Delete all messages")
        }
        LazyColumn(modifier = Modifier.padding(15.dp), verticalArrangement = Arrangement.spacedBy(15.dp)) {
            items(listMessages) {msg ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.height(60.dp).padding(15.dp))
                    {
                        Text(msg)
                    }
                }
            }
        }
    }
}