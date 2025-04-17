package com.example.todolist.views.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todolist.ui.theme.TODOLISTTheme

@Composable
fun SettingsView() {

   Column(
       Modifier.fillMaxSize(),
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.Center
   ) {
       Text("Hello, Higino")

       Button(onClick = {}) {
           Text("Sair")
       }
   }
}



@Preview(showBackground = true)
@Composable
fun SettingsViewreview() {
    TODOLISTTheme {
        SettingsView()
    }
}