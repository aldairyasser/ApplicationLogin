import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.applicationlogin.User

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(user: User) {
    val messagesListState = remember { mutableStateListOf<String>() } //Para alamacenar los mensajes
    var message by remember { mutableStateOf("") } // Los mensajes
    var selectedMessageIndex by remember { mutableStateOf(-1) } // Se inicializa en 0 para indicar que no hay ningún mensaje seleccionado por defecto
    var showBottomSheet by remember { mutableStateOf(false) } // Para mostrar el bottom sheet, el cual empieza en false

    Scaffold(
        topBar = { TopBarChat(user) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.fillMaxWidth().padding(45.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f).padding(16.dp)
                ) {
                    itemsIndexed(messagesListState) {index, msg -> //Utilizo itemIndex para que me de el índice correcto.
                        MessageBubble(msg, onMoreOptionsClick = {
                            selectedMessageIndex = index // Guardamos el índice del mensaje seleccionado
                            showBottomSheet = true
                        })
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                if (message.isNotBlank()) {
                                    messagesListState.add(message)
                                    message = ""
                                }
                            }
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    ElevatedButton(
                        onClick = {
                            if (message.isNotBlank()) {
                                messagesListState.add(message)
                                message = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text("Enviar", color = Color.White)
                    }
                }
            }
        }
    )

    // Bottom Sheet para eliminar el texto
    if (showBottomSheet) {
        ModalBottomSheet(onDismissRequest = { showBottomSheet = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Opciones de mensaje", fontSize = 20.sp, textAlign = TextAlign.Center)

                Spacer(modifier = Modifier.height(16.dp))

                ElevatedButton(
                    onClick = {
                        // Verificar que el índice sea válido antes de eliminar
                        if (selectedMessageIndex in messagesListState.indices) {
                            messagesListState.removeAt(selectedMessageIndex)
                        }
                        showBottomSheet = false
                        selectedMessageIndex = -1 // Restablecer el índice después de eliminar
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Eliminar mensaje", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun MessageBubble(message: String, onMoreOptionsClick: () -> Unit) { //Le pasamos los parametro creados para mostrar el mensaje y para darle click a las opciones
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = message,//Mostramos el mensaje escrito
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )

            // Tres puntos verticales para el menú de opciones
            IconButton(onClick = { onMoreOptionsClick.invoke() }) { //Incluimos el icono de las opciones
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Opciones"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarChat(user: User) {
    TopAppBar(
        title = {
            Text(
                text = "${user.name} Chat",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp),
                textAlign = TextAlign.Center,
                fontSize = 35.sp
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.White
        )
    )
}
