package pe.edu.upeu.pharmamobil.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pe.edu.upeu.pharmamobil.domain.model.Producto

private val productosSimulados = listOf(
    Producto(1, "Paracetamol", 8.50, 100),
    Producto(2, "Ibuprofeno", 12.00, 50),
    Producto(3, "Amoxicilina", 18.50, 20)
)

sealed class ResultadoProductos {
    data object Cargando : ResultadoProductos()

    data class Exito(
        val list: List<Producto>
    ) : ResultadoProductos()

    data class Error(
        val msg: String
    ) : ResultadoProductos()
}

suspend fun obtenerProductos(): List<Producto> {
    delay(1000)
    return productosSimulados
}

fun observarEstados(): Flow<String> = flow {
    emit("Iniciando")
    delay(1000)
    emit("Finalizado")
}

fun observarProductos(): Flow<List<Producto>> = flow {
    emit(emptyList())
    delay(1000)
    emit(productosSimulados.map { it.copy() })
}

fun cargarProductos(): Flow<ResultadoProductos> = flow {
    emit(ResultadoProductos.Cargando)
    delay(1000)

    try {
        emit(
            ResultadoProductos.Exito(
                productosSimulados.map { it.copy() }
            )
        )
    } catch (exception: Exception) {
        emit(
            ResultadoProductos.Error(
                exception.message ?: "Error al cargar productos"
            )
        )
    }
}