package com.example.pc01capcha23100750elias23101084.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuPrincipalScreen(
    onNavigateToCalculadora: () -> Unit,
    onNavigateToPlanificador: () -> Unit,
    onNavigateToCatalogo: () -> Unit,
    onNavigateToUbicacion: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Menú Principal de Navegación") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onNavigateToCalculadora,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calculadora de Equipaje")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onNavigateToPlanificador,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Planificador de Presupuesto de Viaje")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onNavigateToCatalogo,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Catálogo de Destinos Turísticos")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onNavigateToUbicacion,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Permiso de Ubicación para Asistencia de Viaje")
            }
        }
    }
}
