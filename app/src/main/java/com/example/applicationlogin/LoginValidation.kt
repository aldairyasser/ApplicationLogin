package com.example.applicationlogin

object LoginValidation {
     fun validateUser( pass: String): Boolean {
        return pass == "admin"
    }
}