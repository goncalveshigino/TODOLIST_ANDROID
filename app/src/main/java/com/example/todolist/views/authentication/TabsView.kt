package com.example.todolist.views.authentication

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.todolist.ui.theme.TODOLISTTheme
import com.example.todolist.viewModels.LoginViewModel

@Composable
fun TabsView(navController: NavController, loginVM: LoginViewModel) {

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Iniciar sessão", "Registrar-se")

    Column {
        TabRow(
            selectedTabIndex = selectedTab,
            indicator = { tabPosition ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPosition[selectedTab])
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selectedTab == index,
                    onClick = { selectedTab = index},
                    text = { Text(text = title)}
                )
            }
        }
        when(selectedTab){
            0 -> LoginView(navController, loginVM)
            1 -> RegisterView(navController, loginVM)
        }
    }

}


@Preview(showBackground = true)
@Composable
fun TabsViewPreview() {
    TODOLISTTheme {
        TabsView(
            navController = TODO(),
            loginVM = TODO()
        )
    }
}