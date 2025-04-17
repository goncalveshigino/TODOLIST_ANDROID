import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolist.viewModels.HomeViewModel
import com.example.todolist.viewModels.LoginViewModel
import com.example.todolist.views.authentication.BlankView
import com.example.todolist.views.authentication.TabsView
import com.example.todolist.views.home.EditTaskView
import com.example.todolist.views.home.HomeView


@Composable
fun NavManager(loginVM: LoginViewModel, homeVM: HomeViewModel){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Blank"){
        composable("Blank"){
            BlankView(navController)
        }
        composable("Login"){
            TabsView(
                navController,
                loginVM
            )
        }
        composable("Home"){
            HomeView(navController, homeVM)
        }
        composable("EditTaskView/{idDod}", arguments = listOf(
            navArgument("idDoc") { type = NavType.StringType}
        )){
            val idDoc = it.arguments?.getString("idDoc")?: ""

            EditTaskView(navController, homeVM, idDoc)
        }
    }
}