package pe.edu.upeu.pharmamobil.presentation.producto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pe.edu.upeu.pharmamobil.domain.model.Producto
import pe.edu.upeu.pharmamobil.domain.model.productosDemo
import pe.edu.upeu.pharmamobil.presentation.components.ValidatedTextField

@Composable
fun ProductoScreen(
    onProductoRegistrado: (Producto) -> Unit = {}
) {
    // Estados observables para los campos de texto
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }

    // Estados de error individuales
    var nombreError by remember { mutableStateOf<String?>(null) }
    var precioError by remember { mutableStateOf<String?>(null) }
    var stockError by remember { mutableStateOf<String?>(null) }

    // Estado para mensaje de éxito
    var mensajeExito by remember { mutableStateOf<String?>(null) }

    // ⭐ CONTROL PARA EVITAR ERRORES PREMATUROS
    var intentoRegistrar by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("PharmaMobil")
        Text("Registro de Producto")

        ValidatedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                // Limpiar error cuando el usuario empieza a escribir
                if (intentoRegistrar && nombreError != null) {
                    nombreError = null
                }
            },
            label = "Nombre:",
            error = if (intentoRegistrar) nombreError else null,
            modifier = Modifier.fillMaxWidth()
        )

        ValidatedTextField(
            value = precio,
            onValueChange = {
                precio = it
                if (intentoRegistrar && precioError != null) {
                    precioError = null
                }
            },
            label = "Precio",
            error = if (intentoRegistrar) precioError else null,
            modifier = Modifier.fillMaxWidth()
        )

        ValidatedTextField(
            value = stock,
            onValueChange = {
                stock = it
                if (intentoRegistrar && stockError != null) {
                    stockError = null
                }
            },
            label = "Stock",
            error = if (intentoRegistrar) stockError else null,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                // ⭐ MARCAR QUE SE INTENTÓ REGISTRAR
                intentoRegistrar = true
                mensajeExito = null

                // ⭐ VALIDACIÓN SECUENCIAL CON WHEN
                val hayError = when {
                    // 1. Validar nombre (primero)
                    ProductoValidator.validarNombre(nombre) != null -> {
                        nombreError = ProductoValidator.validarNombre(nombre)
                        true
                    }
                    // 2. Validar precio (segundo)
                    ProductoValidator.validarPrecio(precio) != null -> {
                        precioError = ProductoValidator.validarPrecio(precio)
                        true
                    }
                    // 3. Validar stock (tercero)
                    ProductoValidator.validarStock(stock) != null -> {
                        stockError = ProductoValidator.validarStock(stock)
                        true
                    }
                    // 4. ÉXITO: todos pasaron
                    else -> false
                }

                if (!hayError) {
                    // ⭐ INSTANCIAR EL OBJETO PRODUCTO
                    val producto = Producto(
                        id = 0L,
                        nombre = nombre.trim(),
                        precio = precio.toDoubleOrNull()!!,  // ← Cambiado
                        stock = stock.toIntOrNull()!!         // ← Cambiado
                    )

                    // ⭐ NOTIFICAR A QUIEN CORRESPONDA
                    onProductoRegistrado(producto)

                    // ⭐ MOSTRAR MENSAJE DE ÉXITO
                    mensajeExito = "Producto \"$nombre\" registrado correctamente"

                    // ⭐ LIMPIEZA AUTOMÁTICA DE CAMPOS
                    nombre = ""
                    precio = ""
                    stock = ""

                    // ⭐ RESETEAR INTENTO PARA PRÓXIMO REGISTRO
                    intentoRegistrar = false
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }

        // ⭐ MOSTRAR MENSAJE DE ÉXITO SI EXISTE
        mensajeExito?.let {
            Text(it)
        }
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Inventario",
            style = MaterialTheme.typography.headlineSmall
        )

        InventarioTabs(productos = productosDemo)
    }
}