package com.example.todolist.services

import android.util.Log
import com.example.todolist.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class AuthService {

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    Log.d("Erro no firebase", "Email e senha incorretos")
                }
            }
    }

    fun registerUser(
        email: String,
        password: String,
        userName: String,
        onSuccess: () -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    saveUser(userName)
                    onSuccess()
                } else {
                    Log.d("Erro ao guardar", "ERRO ao guardar no Firebase")
                }
            }
    }

    private fun saveUser(username: String) {
        val id = auth.currentUser?.uid
        val email = auth.currentUser?.email

        val user = UserModel(
            userId = id.toString(),
            email = email.toString(),
            username = username
        )

        firestore.collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d("GUARDADO", "Guardou corretamente")
            }
            .addOnFailureListener {
                Log.d("Erro ao guardar", "ERRO ao guardar no Firebase")
            }
    }
}
