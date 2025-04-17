package com.example.todolist.views

import NavManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.todolist.ui.theme.TODOLISTTheme
import com.example.todolist.viewModels.HomeViewModel
import com.example.todolist.viewModels.LoginViewModel
import com.example.todolist.views.authentication.TabsView
import com.example.todolist.views.home.HomeView
import com.example.todolist.views.settings.SettingsView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginVM : LoginViewModel by viewModels()
        val homeVM : HomeViewModel by viewModels()

        setContent {
            TODOLISTTheme {
                Surface {
                    NavManager(loginVM, homeVM)
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun AppViewPreview() {
    TODOLISTTheme {
        TabsView(
            navController = TODO(),
            loginVM = TODO()
        )
    }
}