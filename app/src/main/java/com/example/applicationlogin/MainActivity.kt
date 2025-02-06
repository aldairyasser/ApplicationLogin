package com.example.applicationlogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController() // Controlador de navegación

            NavHost(
                navController = navController,
                startDestination = "Login" // Pantalla inicial
            ) {
                composable("Login") {
                    LoginScreen { user ->
                        navController.navigate("Chat/${user.username}")
                    }
                }
                composable(
                    "chat/{userName}",
                    arguments = listOf(navArgument("userName") { type = NavType.StringType })
                ) { backStackEntry ->
                    // Recuperamos el nombre del usuario desde los argumentos
                    val userName = backStackEntry.arguments?.getString("userName") ?: ""
                    val user = User(userName) // Creamos el objeto User con el nombre
                    ChatScreen(user)
                }
            }
        }
    }
}
