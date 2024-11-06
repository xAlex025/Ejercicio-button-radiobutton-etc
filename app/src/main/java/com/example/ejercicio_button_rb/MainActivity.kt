package com.example.ejercicio_button_rb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiAplicacion()
        }
    }
}

@Composable
fun MiAplicacion() {
    var progresoVisible by remember { mutableStateOf(false) }
    var textoBoton by remember { mutableStateOf("Presionar") }
    val coroutine = rememberCoroutineScope()
    var textoVisible by remember { mutableStateOf(false) }
    var interruptorChecked by remember { mutableStateOf(false) }
    var opcionSeleccionada by remember { mutableStateOf("") }
    var indiceImagenActual by remember { mutableStateOf(0) }
    val imagenes = listOf(
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.LightGray)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Icono",
                    tint = Color.Yellow,
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = {
                    textoBoton = "Botón presionado"
                    progresoVisible = true
                    coroutine.launch {
                        delay(5000)
                        progresoVisible = false
                        textoBoton = "Presionar"
                    }
                }) {
                    Text(textoBoton)
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (progresoVisible) {
                    CircularProgressIndicator()
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = textoVisible,
                        onCheckedChange = { textoVisible = it }
                    )
                    Text("Activar")
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (textoVisible) {
                    Text(
                        text = if (opcionSeleccionada.isNotEmpty()) {
                            "Seleccionaste: $opcionSeleccionada"
                        } else {
                            "Mensaje Visible"
                        }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Cyan)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Switch(
                    checked = interruptorChecked,
                    onCheckedChange = { interruptorChecked = it }
                )
                Text("Mostrar opciones")

                Spacer(modifier = Modifier.height(16.dp))

                if (interruptorChecked) {
                    grupos(
                        opciones = listOf("Opción 1", "Opción 2", "Opción 3"),
                        opcionSeleccionada = opcionSeleccionada,
                        OpcionSeleccionada_1 = { opcionSeleccionada = it }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Yellow)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imagenes[indiceImagenActual]),
                contentDescription = "Imágenes",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        indiceImagenActual = (indiceImagenActual + 1) % imagenes.size
                    }
            )
        }
    }
}

@Composable
fun grupos(
    opciones: List<String>,
    opcionSeleccionada: String,
    OpcionSeleccionada_1: (String) -> Unit
) {
    opciones.forEach { opcion ->
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = (opcion == opcionSeleccionada),
                onClick = { OpcionSeleccionada_1(opcion) }
            )
            Text(text = opcion)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VistaPreviaPredeterminada() {
    MiAplicacion()
}
