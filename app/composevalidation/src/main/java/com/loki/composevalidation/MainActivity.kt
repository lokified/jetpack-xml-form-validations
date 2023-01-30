package com.loki.composevalidation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.loki.composevalidation.home.HomeScreen
import com.loki.composevalidation.login.LoginScreen
import com.loki.composevalidation.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Navigation()
            }
        }
    }
}


@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavGraph.LoginScreen.route
    ) {

        composable(route = NavGraph.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

        composable(route = NavGraph.HomeScreen.route) {
            HomeScreen()
        }
    }
}


sealed class NavGraph(val route: String) {
    object LoginScreen: NavGraph("login_screen")
    object HomeScreen: NavGraph("home_screen")
}