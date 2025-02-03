package com.example.applicationlogin

object LoginValidation {
     fun validateUser(user: String, pass: String): Boolean {
        return user == "admin" && pass == "admin"
    }
}