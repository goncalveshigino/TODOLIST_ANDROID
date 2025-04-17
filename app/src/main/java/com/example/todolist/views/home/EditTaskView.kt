package com.example.todolist.views.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolist.viewModels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskView(navController: NavController, homeVM: HomeViewModel, idDoc: String){
    LaunchedEffect(Unit) {
        homeVM.getTaskById(idDoc)
    }
    val state = homeVM.state
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar tarefa") },
                actions = {
                    IconButton(onClick = {
                        homeVM.deleteTask(idDoc) {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                    }
                }
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = state.title,
                onValueChange = { homeVM.onValue(it, "title")},
                label = { Text(text = "Atualizar Tarefa") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                homeVM.updateTask(idDoc) {
                    navController.popBackStack()
                }
            },
                Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                if (homeVM.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .height(24.dp)
                            .padding(2.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Atualizar")
                }
            }
        }
    }
}