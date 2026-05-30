package com.example.pc01capcha23100750elias23101084.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.util.Locale

data class Destination(
    val country: String,
    val city: String,
    val averageCost: Double,
    val imageUrl: String,
    val flagUrl: String
)

val destinations = listOf(
    Destination(
        "Perú", "Cusco", 1200.0,
        "https://images.unsplash.com/photo-1587595431973-160d0d94add1?q=80&w=800&auto=format&fit=crop",
        "https://flagcdn.com/w320/pe.png"
    ),
    Destination(
        "Francia", "París", 2500.0,
        "https://images.unsplash.com/photo-1499856871958-5b9627545d1a?q=80&w=800&auto=format&fit=crop",
        "https://flagcdn.com/w320/fr.png"
    ),
    Destination(
        "Japón", "Tokio", 3000.0,
        "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?q=80&w=800&auto=format&fit=crop",
        "https://flagcdn.com/w320/jp.png"
    ),
    Destination(
        "Italia", "Roma", 2200.0,
        "https://images.unsplash.com/photo-1552832230-c0197dd311b5?q=80&w=800&auto=format&fit=crop",
        "https://flagcdn.com/w320/it.png"
    ),
    Destination(
        "México", "Cancún", 1500.0,
        "https://images.unsplash.com/photo-1518105779142-d975f22f1b0a?q=80&w=800&auto=format&fit=crop",
        "https://flagcdn.com/w320/mx.png"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen() {
    val totalDestinations = destinations.size
    val totalCost = destinations.sumOf { it.averageCost }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Explora el Mundo",
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(destinations) { destination ->
                EnhancedDestinationItem(destination)
            }

            item {
                SummaryCard(totalDestinations, totalCost)
            }
        }
    }
}

@Composable
fun EnhancedDestinationItem(destination: Destination) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Main Destination Image
            AsyncImage(
                model = destination.imageUrl,
                contentDescription = "Imagen de ${destination.city}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Gradient Overlay for readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f)),
                            startY = 350f
                        )
                    )
            )

            // Top Content: Flag
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White.copy(alpha = 0.9f),
                    shadowElevation = 4.dp
                ) {
                    AsyncImage(
                        model = destination.flagUrl,
                        contentDescription = "Bandera de ${destination.country}",
                        modifier = Modifier
                            .padding(4.dp)
                            .size(44.dp, 28.dp)
                            .clip(RoundedCornerShape(6.dp)),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }

            // Bottom Content: Info
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(24.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${destination.city}, ${destination.country}",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.95f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Payments,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onTertiaryContainer,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Presupuesto: USD ${String.format(Locale.US, "%,.2f", destination.averageCost)}",
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryCard(total: Int, cost: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Resumen del Catálogo",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f)
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SummaryItem("Destinos", total.toString())
                SummaryItem("Total Estimado", "USD ${String.format(Locale.US, "%,.0f", cost)}")
            }
        }
    }
}

@Composable
fun SummaryItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
            letterSpacing = 1.sp
        )
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
