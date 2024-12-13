package com.dam23_24.composecatalogolayout.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/*
Actividad 1 TERMINADO:
Hacer que el texto del botón muestre "Cargar perfil", pero cambie a "Cancelar"
cuando se muestre la línea de progreso... Cuando pulsemos "Cancelar" vuelve al texto por defecto.
*/
@Preview(showBackground = true)
@Composable
fun Actividad1() {
    var showLoading by rememberSaveable { mutableStateOf(false) }
    val Padding by remember { derivedStateOf { if (showLoading) 30.dp else 0.dp } }

    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (showLoading) {
            CircularProgressIndicator(
                color = Color.Red,
                strokeWidth = 10.dp
            )
            LinearProgressIndicator(
                modifier = Modifier.padding(top = 32.dp),
                color = Color.Red,
                trackColor = Color.LightGray
            )
        }

        Spacer(modifier = Modifier.height(Padding))

        Button(
            onClick = { showLoading = !showLoading }
        ) {
            Text(text = if (showLoading) "Cancelar" else "Cargar perfil")
        }
    }
}

/*
Actividad 2 TERMINADO:
Modifica ahora también que se separe el botón de la línea de progreso 30 dp,
pero usando un estado... es decir, cuando no sea visible no quiero que tenga la separación
aunque no se vea.
*/


/*
Actividad 3 TERMINADO:
- Separar los botones entre ellos 10 dp, del indicador de progreso 15 dp y centrarlos horizontalmente.
- Cuando se clique el botón Incrementar, debe añadir 0.1 a la propiedad progress y quitar 0.1
  cuando se pulse el botón Decrementar.
- Evitar que nos pasemos de los márgenes de su propiedad progressStatus (0-1)
*/
@Composable
fun Actividad3() {
    var progressStatus by rememberSaveable { mutableStateOf(0f) }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Indicador de progreso
        LinearProgressIndicator(
            progress = progressStatus,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    progressStatus = (progressStatus + 0.1f).coerceIn(0f, 1f)
                },
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Text(text = "Incrementar")
            }

            Button(
                onClick = {
                    progressStatus = (progressStatus - 0.1f).coerceIn(0f, 1f)
                }
            ) {
                Text(text = "Reducir")
            }
        }
    }
}

/*
Actividad 4:
Sitúa el TextField en el centro de la pantalla y haz que reemplace el valor de una coma por un punto
y que no deje escribir más de un punto decimal...
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Actividad4() {
    var myVal by rememberSaveable { mutableStateOf("") }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = myVal,
            onValueChange = {
                // reemplazar comas con puntos
                val sanitizedInput = it.replace(",", ".")

                // que solo te deje un punto decimal
                if (sanitizedInput.count { char -> char == '.' } <= 1) {
                    myVal = sanitizedInput
                }
            },
            label = { Text(text = "Importe") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


/*
Actividad 5:
Haz lo mismo, pero elevando el estado a una función superior y usando un componente OutlinedTextField
al que debes añadir un padding alrededor de 15 dp y establecer colores diferentes en los bordes
cuando tenga el foco y no lo tenga.
A nivel funcional no permitas que se introduzcan caracteres que invaliden un número decimal.
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Actividad5() {
    var myVal by rememberSaveable { mutableStateOf("") }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        funcionElevada(
            value = myVal,
            onValueChange = { myVal = it }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun funcionElevada(value: String, onValueChange: (String) -> Unit) {
    fun processInput(input: String): String {
        val sanitizedInput = input.replace(",", ".")
        
        return sanitizedInput.filterIndexed { index, char ->
            char.isDigit() || (char == '.' && sanitizedInput.indexOf('.') == index)
        }
    }

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(processInput(it)) },
        label = { Text(text = "Importe") },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Red,
            unfocusedBorderColor = Color.Green
        )
    )
}
