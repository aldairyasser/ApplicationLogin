package com.example.applicationlogin

sealed class Screen (val route: String){
    object Login : Screen ("Login")
    object Chat : Screen ("Chat/{user}"){
        fun nameChat (user: String ) = "chat/$user"
    }
}