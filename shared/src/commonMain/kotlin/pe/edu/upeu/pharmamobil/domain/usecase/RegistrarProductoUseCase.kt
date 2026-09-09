package pe.edu.upeu.pharmamobil.domain.usecase

import pe.edu.upeu.pharmamobil.domain.model.Producto
import pe.edu.upeu.pharmamobil.domain.repository.ProductoRepository

class RegistrarProductoUseCase(
    private val repository: ProductoRepository
) {
    suspend operator fun invoke(
        nombre: String,
        categoria: String,
        precio: Double,
        stock: Int
    ): Result<Producto> {
        if (nombre.isBlank()) {
            return Result.failure(IllegalArgumentException("El nombre es obligatorio"))
        }
        if (precio <= 0.0) {
            return Result.failure(IllegalArgumentException("El precio debe ser mayor a 0"))
        }
        if (stock < 0) {
            return Result.failure(IllegalArgumentException("El stock no puede ser negativo"))
        }
        return try {
            val nuevo = Producto(nombre = nombre, categoria = categoria, precio = precio, stock = stock)
            Result.success(repository.registrar(nuevo))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}