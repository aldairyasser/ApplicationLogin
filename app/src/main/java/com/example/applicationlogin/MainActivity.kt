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
                         val username = User(user) //Creamos la clase User en vez del Srtring
                        navController.navigate(Chat(username)) //Le pasamos la clase User creada
                    }
                }
                composable<Chat> {
                    val login = it.toRoute<Chat>()
                    ChatScreen(login.user)
                }
            }
        }
    }
}
