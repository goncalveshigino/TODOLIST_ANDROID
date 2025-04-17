package com.example.todolist.views.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.todolist.components.CardTask
import com.example.todolist.ui.theme.TODOLISTTheme
import com.example.todolist.viewModels.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController, homeVM: HomeViewModel) {

    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        homeVM.fetchTask()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                }
            ) {
                Text("Add")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text( text = "Minhas tarefas") },
                navigationIcon = {
                    IconButton(onClick = {
                        homeVM.logOut()
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "")
                    }
                }
            )
        }
    ) { innerPadding ->
       Column(
           modifier = Modifier.padding(innerPadding),
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           val tasks by homeVM.tasksData.collectAsState()

           LazyColumn {
              items(tasks) { item ->
                  CardTask(title = item.title, date = item.date) {
                        navController.navigate("EditTaskView/${item.idDoc}")
                  }
              }
           }
       }


        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {

              Surface(
                    shape = RoundedCornerShape(16.dp),
                    tonalElevation = 8.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                    ) {
                        Text("Título", Modifier.padding(bottom = 10.dp))

                        TextField(
                            value = homeVM.taskTitle,
                            onValueChange = { homeVM.onTaskTitleChange(it) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                homeVM.saveNewTask(){
                                    Toast.makeText(context,"Guardado", Toast.LENGTH_SHORT).show()
                                    showDialog = false
                                }

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                "Salvar",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        TextButton(
                            onClick = { showDialog = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Cancelar",
                               fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    TODOLISTTheme {
        HomeView(
            navController = TODO(),
            homeVM = TODO()
        )
    }
}