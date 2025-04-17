package com.example.todolist.views.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.todolist.viewModels.HomeViewModel

@Composable
fun EditTaskView(navController: NavController, homeVM: HomeViewModel, idDoc: String){
    Text(text = idDoc)
}