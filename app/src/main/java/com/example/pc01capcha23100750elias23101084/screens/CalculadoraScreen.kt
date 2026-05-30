package com.example.pc01capcha23100750elias23101084.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculadoraScreen(navController: NavController) {
    var pesoInput by remember { mutableStateOf("") }
    var tipoVuelo by remember { mutableStateOf("Nacional") }
    var mensajeResultado by remember { mutableStateOf("") }
    var errorPeso by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Calculadora de Equipaje") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Control de Peso de Equipaje",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = pesoInput,
                onValueChange = {
                    pesoInput = it
                    errorPeso = null
                },
                label = { Text("Peso de la maleta (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = errorPeso != null,
                modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
            )

            if (errorPeso != null) {
                Text(
                    text = errorPeso ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start).padding(bottom = 16.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(
                text = "Seleccione Tipo de Vuelo:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (tipoVuelo == "Nacional"),
                    onClick = { tipoVuelo = "Nacional" }
                )
                Text("Nacional (Máx. 23 kg)", modifier = Modifier.padding(end = 16.dp))

                RadioButton(
                    selected = (tipoVuelo == "Internacional"),
                    onClick = { tipoVuelo = "Internacional" }
                )
                Text("Internacional (Máx. 32 kg)")
            }

            Button(
                onClick = {
                    if (pesoInput.isEmpty() || pesoInput == " ") {
                        errorPeso = "Este campo es obligatorio."
                        mensajeResultado = ""
                        return@Button
                    }
                    val peso = pesoInput.toDoubleOrNull()
                    if (peso == null) {
                        errorPeso = "Debe ingresar un valor numérico válido."
                        mensajeResultado = ""
                        return@Button
                    }
                    if (peso <= 0.0) {
                        errorPeso = "El peso debe ser mayor a cero."
                        mensajeResultado = ""
                        return@Button
                    }

                    val limiteMaximo = if (tipoVuelo == "Nacional") 23.0 else 32.0
                    if (peso <= limiteMaximo) {
                        mensajeResultado = "OK: El peso ($peso kg) está dentro del límite permitido para vuelo $tipoVuelo."
                    } else {
                        val excedido = peso - limiteMaximo
                        val excedidoRedondeado = ((excedido * 100).toInt()) / 100.0
                        mensajeResultado = "ALERTA: Excede el límite de vuelo $tipoVuelo.\nCantidad de kg excedidos: $excedidoRedondeado kg."
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
            ) {
                Text("Validar y Calcular")
            }

            if (mensajeResultado.isNotEmpty()) {
                val esAlerta = mensajeResultado.startsWith("ALERTA")
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (esAlerta) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = mensajeResultado,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Regresar al Menú")
            }
        }
    }
}