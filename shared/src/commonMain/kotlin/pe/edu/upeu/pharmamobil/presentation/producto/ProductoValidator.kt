package pe.edu.upeu.pharmamobil.presentation.producto

object ProductoValidator {
    fun validarNombre(nombre: String): String? {
        return if (nombre.isBlank()) {
            "El nombre es obligatorio."
        } else null
    }

    fun validarPrecio(precio: String): String? {
        return when {
            precio.toDoubleOrNull() == null -> "Ingrese un precio numérico."
            precio.toDoubleOrNull()!! <= 0.0 -> "El precio debe ser mayor que cero."
            else -> null
        }
    }

    fun validarStock(stock: String): String? {
        return when {
            stock.toIntOrNull() == null -> "Ingrese un stock entero."
            stock.toIntOrNull()!! < 0 -> "El stock no puede ser negativo."
            else -> null
        }
    }
}