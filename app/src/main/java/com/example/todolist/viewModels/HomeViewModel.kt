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


    private val _tasksData = MutableStateFlow<List<TaskModel>>(emptyList())
    val tasksData: StateFlow<List<TaskModel>> = _tasksData

    fun onTaskTitleChange(newTaskTitle: String) {
        taskTitle = newTaskTitle
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

    fun saveNewTask(onSuccess:() -> Unit) {
            val email = auth.currentUser?.email
        viewModelScope.launch(Dispatchers.IO) {
            try {
//                val newTask = hashMapOf(
//                    "title" to taskMessage,
//                    "date" to formatDate(),
//                    "userEmail" to email.toString()
//                )
                val newTask = TaskModel(
                    userEmail = email.toString(),
                    title = taskTitle,
                    date = formatDate()
                )

                firestore.collection("tasks").add(newTask)
                    .addOnSuccessListener { documentRef ->
                        val docId = documentRef.id

                        firestore.collection("tasks").document(docId).update("idDoc", docId)
                        onSuccess()
                    }
            } catch(e: Exception) {
                Log.d("Erro save", "Erro ao guardar ${e.localizedMessage}")
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