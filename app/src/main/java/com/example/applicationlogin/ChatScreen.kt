import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    val messagesListState = remember { mutableStateListOf<String>() }
    var message by remember { mutableStateOf("") }
    var selectedMessageIndex by remember { mutableStateOf(-1) }
    var showBottomSheet by remember { mutableStateOf(false) }

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
                    items(messagesListState) { msg ->
                        MessageBubble(msg, onMoreOptionsClick = { index ->
                            selectedMessageIndex = index
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

    // Bottom Sheet to delete message
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
                        // Eliminar mensaje
                        if (selectedMessageIndex >= 0) {
                            messagesListState.removeAt(selectedMessageIndex)
                        }
                        showBottomSheet = false
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
fun MessageBubble(message: String, onMoreOptionsClick: (Int) -> Unit) {
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
                text = message,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )

            // Tres puntos verticales para el menú de opciones
            IconButton(onClick = { onMoreOptionsClick.invoke(0) }) {
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
