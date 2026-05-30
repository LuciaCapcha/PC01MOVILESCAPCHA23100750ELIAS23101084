package com.example.pc01capcha23100750elias23101084.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

data class Gasto(val descripcion: String, val monto: Double)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanificadorScreen() {
    var presupuestoTotal by remember { mutableStateOf("") }
    var itemDescripcion by remember { mutableStateOf("") }
    var itemMonto by remember { mutableStateOf("") }
    val listaGastos = remember { mutableStateListOf<Gasto>() }

    val presupuesto = presupuestoTotal.toDoubleOrNull() ?: 0.0
    val gastado = listaGastos.sumOf { it.monto }
    val disponible = presupuesto - gastado

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planificador de Presupuesto") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    titleContentColor = MaterialTheme.colorScheme.onTertiary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = presupuestoTotal,
                onValueChange = { presupuestoTotal = it },
                label = { Text("Presupuesto Total (USD)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                leadingIcon = { Icon(Icons.Default.Wallet, contentDescription = null) }
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Agregar Gasto", fontWeight = FontWeight.Bold)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = itemDescripcion,
                            onValueChange = { itemDescripcion = it },
                            label = { Text("Concepto") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = itemMonto,
                            onValueChange = { itemMonto = it },
                            label = { Text("$") },
                            modifier = Modifier.width(80.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                        )
                        IconButton(onClick = {
                            val m = itemMonto.toDoubleOrNull()
                            if (itemDescripcion.isNotBlank() && m != null) {
                                listaGastos.add(Gasto(itemDescripcion, m))
                                itemDescripcion = ""
                                itemMonto = ""
                            }
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "Agregar")
                        }
                    }
                }
            }

            Text("Lista de Gastos", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            
            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listaGastos) { gasto ->
                    ListItem(
                        headlineContent = { Text(gasto.descripcion) },
                        trailingContent = { 
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("$${gasto.monto}", fontWeight = FontWeight.Bold)
                                IconButton(onClick = { listaGastos.remove(gasto) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    )
                    HorizontalDivider()
                }
            }

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (disponible >= 0) MaterialTheme.colorScheme.secondaryContainer 
                                     else MaterialTheme.colorScheme.errorContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Total Gastado", style = MaterialTheme.typography.labelMedium)
                        Text("$${String.format("%.2f", gastado)}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Disponible", style = MaterialTheme.typography.labelMedium)
                        Text("$${String.format("%.2f", disponible)}", 
                            style = MaterialTheme.typography.titleLarge, 
                            fontWeight = FontWeight.Bold,
                            color = if (disponible >= 0) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}
