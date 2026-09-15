package pe.edu.upeu.pharmamobil.presentation.producto

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoScreen(
    viewModel: ProductoViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Gestión de Productos",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Nuevo Producto",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )

                    OutlinedTextField(
                        value = uiState.formulario.nombre,
                        onValueChange = viewModel::onNombreChange,
                        label = { Text("Nombre") },
                        placeholder = { Text("Ej: Paracetamol 500mg") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.formulario.errorNombre != null,
                        supportingText = uiState.formulario.errorNombre?.let { { Text(it) } },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = uiState.formulario.categoria,
                        onValueChange = viewModel::onCategoriaChange,
                        label = { Text("Categoría") },
                        placeholder = { Text("Ej: Analgésicos") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = uiState.formulario.precio,
                            onValueChange = viewModel::onPrecioChange,
                            label = { Text("Precio") },
                            placeholder = { Text("0.00") },
                            modifier = Modifier.weight(1f),
                            isError = uiState.formulario.errorPrecio != null,
                            supportingText = uiState.formulario.errorPrecio?.let { { Text(it) } },
                            singleLine = true,
                            prefix = { Text("S/ ") }
                        )

                        OutlinedTextField(
                            value = uiState.formulario.stock,
                            onValueChange = viewModel::onStockChange,
                            label = { Text("Stock") },
                            placeholder = { Text("0") },
                            modifier = Modifier.weight(1f),
                            isError = uiState.formulario.errorStock != null,
                            supportingText = uiState.formulario.errorStock?.let { { Text(it) } },
                            singleLine = true
                        )
                    }

                    Button(
                        onClick = viewModel::registrarProducto,
                        enabled = !uiState.formulario.guardando,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = if (uiState.formulario.guardando) "Guardando..." else "Registrar Producto",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "Inventario",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        when (val fase = uiState.fase) {
            is ProductoListaFase.Cargando -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            is ProductoListaFase.SinProductos -> {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Inventory2,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = "No hay productos registrados",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }

            is ProductoListaFase.Error -> {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onErrorContainer
                            )
                            Text(
                                text = "Error: ${fase.mensaje}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }
            }

            is ProductoListaFase.ConProductos -> {
                items(fase.productos) { producto ->
                    ProductoCard(producto)
                }
            }
        }
    }
}

@Composable
private fun ProductoCard(producto: pe.edu.upeu.pharmamobil.domain.model.Producto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.weight(1f)
                )

                if (producto.requiereReposicion()) {
                    AssistChip(
                        onClick = { },
                        label = { Text("Reponer", fontSize = 12.sp) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    )
                }
            }

            Text(
                text = producto.categoria,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "S/ ${producto.precio.toString().takeIf { it.contains(".") } ?: "${producto.precio}.00"}",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Inventory2,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = if (producto.requiereReposicion())
                            MaterialTheme.colorScheme.error
                        else
                            MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = "${producto.stock} un.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (producto.requiereReposicion())
                            MaterialTheme.colorScheme.error
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}