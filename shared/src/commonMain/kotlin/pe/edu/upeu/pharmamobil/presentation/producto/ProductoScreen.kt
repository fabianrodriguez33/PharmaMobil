package pe.edu.upeu.pharmamobil.presentation.producto

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductoScreen(
    viewModel: ProductoViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = uiState.formulario.nombre,
            onValueChange = viewModel::onNombreChange,
            label = { Text("Nombre") },
            isError = uiState.formulario.errorNombre != null,
            supportingText = { uiState.formulario.errorNombre?.let { Text(it) } }
        )
        OutlinedTextField(
            value = uiState.formulario.precio,
            onValueChange = viewModel::onPrecioChange,
            label = { Text("Precio") },
            isError = uiState.formulario.errorPrecio != null,
            supportingText = { uiState.formulario.errorPrecio?.let { Text(it) } }
        )
        OutlinedTextField(
            value = uiState.formulario.stock,
            onValueChange = viewModel::onStockChange,
            label = { Text("Stock") },
            isError = uiState.formulario.errorStock != null,
            supportingText = { uiState.formulario.errorStock?.let { Text(it) } }
        )
        Button(onClick = viewModel::registrarProducto, enabled = !uiState.formulario.guardando) {
            Text(if (uiState.formulario.guardando) "Guardando..." else "Registrar")
        }

        Spacer(Modifier.height(16.dp))

        when (val fase = uiState.fase) {
            is ProductoListaFase.Cargando -> CircularProgressIndicator()
            is ProductoListaFase.SinProductos -> Text("No hay productos registrados")
            is ProductoListaFase.Error -> Text("Error: ${fase.mensaje}")
            is ProductoListaFase.ConProductos -> LazyColumn {
                items(fase.productos) { p ->
                    ListItem(
                        headlineContent = { Text(p.nombre) },
                        supportingContent = { Text("Stock: ${p.stock} · S/ ${p.precio}") },
                        trailingContent = { if (p.requiereReposicion()) Text("Reponer") }
                    )
                }
            }
        }
    }
}