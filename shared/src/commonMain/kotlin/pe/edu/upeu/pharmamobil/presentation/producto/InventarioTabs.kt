package pe.edu.upeu.pharmamobil.presentation.producto

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pe.edu.upeu.pharmamobil.domain.model.Producto
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventarioTabs(productos: List<Producto>) {
    var tabSeleccionada by remember {
        mutableStateOf(0)
    }

    val productosFiltrados = when (tabSeleccionada) {
        0 -> productos.filter { it.stock > 5 }
        1 -> productos.filter { it.stock <= 5 }
        2 -> emptyList()
        else -> emptyList()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SecondaryTabRow(
            selectedTabIndex = tabSeleccionada,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Tab(
                selected = tabSeleccionada == 0,
                onClick = { tabSeleccionada = 0 },
                text = { Text("Stock OK") }
            )

            Tab(
                selected = tabSeleccionada == 1,
                onClick = { tabSeleccionada = 1 },
                text = { Text("Bajo stock") }
            )

            Tab(
                selected = tabSeleccionada == 2,
                onClick = { tabSeleccionada = 2 },
                text = { Text("Otros") }
            )
        }

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(productosFiltrados) { producto ->
                ProductoCard(producto)
            }

            if (productosFiltrados.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay productos en esta categoría",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductoCard(producto: Producto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val precioRedondeado = (producto.precio * 100).roundToInt() / 100.0
                Text(
                    text = "S/ $precioRedondeado",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = "Stock: ${producto.stock}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (producto.stock <= 5)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}