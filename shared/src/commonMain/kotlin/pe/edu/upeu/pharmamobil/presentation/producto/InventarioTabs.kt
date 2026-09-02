package pe.edu.upeu.pharmamobil.presentation.producto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pe.edu.upeu.pharmamobil.domain.model.Producto
import kotlin.math.roundToInt


@Composable
fun InventarioTabs(productos: List<Producto>) {
    var tabSeleccionada by remember {
        mutableStateOf(0)
    }

    val productosFiltrados = when (tabSeleccionada) {
        0 -> productos.filter { it.activo && it.stock > 5 }
        1 -> productos.filter { !it.activo }
        2 -> productos.filter { it.stock <= 5 }
        else -> emptyList()
    }

    Column {
        ScrollableTabRow(
            selectedTabIndex = tabSeleccionada
        ) {
            Tab(
                selected = tabSeleccionada == 0,
                onClick = { tabSeleccionada = 0 },
                text = { Text("Activos") }
            )

            Tab(
                selected = tabSeleccionada == 1,
                onClick = { tabSeleccionada = 1 },
                text = { Text("Inactivos") }
            )

            Tab(
                selected = tabSeleccionada == 2,
                onClick = { tabSeleccionada = 2 },
                text = { Text("Bajo stock") }
            )
        }

        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(productosFiltrados) { producto ->
                ProductoCard(producto)
            }
        }
    }
}

@Composable
private fun ProductoCard(producto: Producto) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val precioRedondeado = (producto.precio * 100).roundToInt() / 100.0
                Text("Precio: S/ $precioRedondeado")
                Text("Stock: ${producto.stock}")
            }

            Text(
                text = when {
                    !producto.activo -> "Estado: Inactivo"
                    producto.stock <= 5 -> "Estado: Bajo stock"
                    else -> "Estado: Activo"
                },
                color = when {
                    !producto.activo ->
                        MaterialTheme.colorScheme.error

                    producto.stock <= 5 ->
                        MaterialTheme.colorScheme.tertiary

                    else ->
                        MaterialTheme.colorScheme.primary
                }
            )
        }
    }
}