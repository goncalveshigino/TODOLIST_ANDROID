package com.example.todolist.viewModels

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.model.TaskModel
import com.example.todolist.services.AuthService
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import kotlin.text.format

class HomeViewModel: ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    var taskTitle by  mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

   var state by mutableStateOf(TaskModel())
       private set


    private val _tasksData = MutableStateFlow<List<TaskModel>>(emptyList())
    val tasksData: StateFlow<List<TaskModel>> = _tasksData


    fun onTaskTitleChange(newTaskTitle: String) {
        taskTitle = newTaskTitle
    }

    fun onValue(value: String, text: String){
        when(text){
            "title" -> state = state.copy(title = value)
        }
    }

    fun fetchTask() {
        val email = auth.currentUser?.email
        firestore.collection("tasks")
            .whereEqualTo("userEmail", email.toString())
            .addSnapshotListener {querySnapshot, error ->
                if(error != null) {
                    return@addSnapshotListener
                }

                val documents = mutableListOf<TaskModel>()

                if(querySnapshot != null) {
                    for (document in querySnapshot) {
                       // val myDocument = document.toObject(TaskModel::class.java)
                        val myDocument = document.toObject(TaskModel::class.java).copy(idDoc = document.id)
                        documents.add(myDocument)
                    }
                }

                _tasksData.value = documents
            }
    }

    fun getTaskById(documentId: String) {
        firestore.collection("tasks")
            .document(documentId)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val task = snapshot.toObject(TaskModel::class.java)
                    state = state.copy(
                        title = task?.title ?: ""
                    )
                }
            }
    }

    fun saveNewTask(onSuccess:() -> Unit) {
            val email = auth.currentUser?.email
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isLoading = true
                val newTask = hashMapOf(
                    "title" to taskTitle,
                    "date" to formatDate(),
                    "userEmail" to email.toString()
                )
//                val newTask = TaskModel(
//                    userEmail = email.toString(),
//                    title = taskTitle,
//                    date = formatDate()
//                )

                firestore.collection("tasks").add(newTask)
                    .addOnSuccessListener {
                        isLoading = false
//                        val docId = documentRef.id
//
//                        firestore.collection("tasks").document(docId).update("idDoc", docId)
                        onSuccess()
                    }
            } catch(e: Exception) {
                isLoading = false
                Log.d("Erro save", "Erro ao guardar ${e.localizedMessage}")
            }
        }
    }

    fun updateTask(idDoc: String, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isLoading = true
                val editTask = hashMapOf("title" to state.title)

                firestore.collection("tasks").document(idDoc)
                    .update(editTask as Map<String, Any>)
                    .addOnSuccessListener {
                        isLoading = false
                        onSuccess()
                    }
            } catch (e: Exception) {
                isLoading = false
                Log.d("Error Edit", "Erro ao Editar dados da tarefa ${e.localizedMessage}")
            }
        }
    }

    fun deleteTask(idDoc: String, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                firestore.collection("tasks").document(idDoc)
                    .delete()
                    .addOnSuccessListener {
                        onSuccess()
                    }
            } catch (e: Exception) {
                Log.d("Error Delete", "Erro ao Eliminar tarefa ${e.localizedMessage}")
            }
        }
    }

    private fun formatDate(): String {
        val currentDate : Date = Calendar.getInstance().time
        val res = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return res.format(currentDate)
    }

    fun logOut(){
        auth.signOut()
    }

}