package com.example.applicationlogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController() // Controlador de navegación

            NavHost(
                navController = navController,
                startDestination = Login // Pantalla inicial
            ) {
                composable<Login> {
                    LoginScreen { user ->
                        navController.navigate(Chat(user)) //Le pasamos la clase User creada
                    }
                }
                composable<Chat> {
                    val username = it.toRoute<Chat>()
                    ChatScreen(username.user)
                }
            }
        }
    }
}
