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
fun PlanificadorScreen(navController: NavController? = null) { // <-- Corrección: Parámetro opcional seguro
    var presupuestoInput by remember { mutableStateOf("") }
    var hospedajeInput by remember { mutableStateOf("") }
    var alimentacionInput by remember { mutableStateOf("") }
    var entretenimientoInput by remember { mutableStateOf("") }

    var mensajeResultado by remember { mutableStateOf("") }
    var errorMensaje by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Planificador de Presupuesto") }) }
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
                text = "Gastos de Viaje",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = presupuestoInput,
                onValueChange = { presupuestoInput = it; errorMensaje = null },
                label = { Text("Presupuesto Total (S/.)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = hospedajeInput,
                onValueChange = { hospedajeInput = it; errorMensaje = null },
                label = { Text("Gasto en Hospedaje (S/.)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = alimentacionInput,
                onValueChange = { alimentacionInput = it; errorMensaje = null },
                label = { Text("Gasto en Alimentación (S/.)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = entretenimientoInput,
                onValueChange = { entretenimientoInput = it; errorMensaje = null },
                label = { Text("Gasto en Entretenimiento (S/.)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            if (errorMensaje != null) {
                Text(
                    text = errorMensaje ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start).padding(bottom = 16.dp)
                )
            }

            Button(
                onClick = {
                    if (presupuestoInput.isEmpty() || hospedajeInput.isEmpty() ||
                        alimentacionInput.isEmpty() || entretenimientoInput.isEmpty()) {
                        errorMensaje = "Todos los campos son obligatorios."
                        mensajeResultado = ""
                        return@Button
                    }

                    val total = presupuestoInput.toDoubleOrNull()
                    val hosp = hospedajeInput.toDoubleOrNull()
                    val alim = alimentacionInput.toDoubleOrNull()
                    val ent = entretenimientoInput.toDoubleOrNull()

                    if (total == null || hosp == null || alim == null || ent == null) {
                        errorMensaje = "Debe ingresar valores numéricos válidos."
                        mensajeResultado = ""
                        return@Button
                    }

                    if (total <= 0 || hosp < 0 || alim < 0 || ent < 0) {
                        errorMensaje = "El presupuesto debe ser mayor a cero y los gastos no pueden ser negativos."
                        mensajeResultado = ""
                        return@Button
                    }

                    val sumaGastos = hosp + alim + ent
                    val saldoRestante = total - sumaGastos

                    if (saldoRestante >= 0) {
                        val saldoRedondeado = ((saldoRestante * 100).toInt()) / 100.0
                        mensajeResultado = "ÉXITO: Presupuesto controlado.\nSaldo restante: S/. $saldoRedondeado"
                    } else {
                        val exceso = -saldoRestante
                        val excesoRedondeado = ((exceso * 100).toInt()) / 100.0
                        mensajeResultado = "ALERTA: Has excedido tu presupuesto.\nMonto excedido: S/. $excesoRedondeado"
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text("Calcular Presupuesto")
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
                onClick = { navController?.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Regresar al Menú")
            }
        }
    }
}