package com.example.todolist.viewModels

import android.util.Log

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.services.AuthService
import kotlinx.coroutines.launch


class LoginViewModel: ViewModel() {

    private val authService = AuthService()

    var email by  mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var userName by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set


    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onUserNameChange(newUserName: String) {
        userName = newUserName
    }

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                isLoading = true
                authService.login(email, password){
                    isLoading = false
                    onSuccess()
                }
            } catch(e: Exception) {
                isLoading = false
                Log.d("ERRO no jetpack", "Error: ${e.localizedMessage}")
           }
        }
    }

    fun createUser(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                isLoading = true
                authService.registerUser(email,password,userName){
                    isLoading = false
                    onSuccess()
                }
            } catch(e: Exception) {
                isLoading = false
                Log.d("ERRO no jetpack", "Error: ${e.localizedMessage}")
            }
        }
    }


}