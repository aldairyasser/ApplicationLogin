package com.example.applicationlogin

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(onSuccess: (userName: String) -> Unit) {
    Scaffold(topBar = { TopBar() }, content = {
        ContentLogin(onSuccess = {
            onSuccess(it)
        })
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(
                text = "LOG IN",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp),
                textAlign = TextAlign.Center,
                fontSize = 35.sp
            )
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black, titleContentColor = Color.White
        )
    )
}

@Composable
fun ContentLogin(onSuccess: (userName: String) -> Unit) {
    var user by rememberSaveable { mutableStateOf("") }
    var pass by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(125.dp)
        )
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp),
            painter = painterResource(id = R.drawable.user),
            contentDescription = "userIcon"
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(100.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = user,
                    placeholder = { Text(text = stringResource(R.string.insert_your_username)) },
                    onValueChange = { user = it }, // Instant synchronize
                    label = { Text(text = "Username") })
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(20.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = pass,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { pass = it }, // Instant synchronize
                placeholder = { Text(text = stringResource(R.string.insert_your_password)) },
                label = { Text(text = "Enter your password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(80.dp)
        )
        ElevatedButton(
            onClick = {
                val isValid = LoginValidation.validateUser(user, pass)
                if (isValid) {
                    Toast.makeText(context, "BIENVENIDO", Toast.LENGTH_LONG).show()
                    onSuccess(user) //Le pasamos el nombre de la ruta a la que deseamos ir.

                } else {
                    Toast.makeText(context, "CORREO O CONTRASEÑA INCORRECTA", Toast.LENGTH_LONG)
                        .show()
                }
            },
            colors = ButtonDefaults.elevatedButtonColors(containerColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(
                text = "LOG IN", color = Color.White, fontSize = 20.sp
            )
        }
    }
}