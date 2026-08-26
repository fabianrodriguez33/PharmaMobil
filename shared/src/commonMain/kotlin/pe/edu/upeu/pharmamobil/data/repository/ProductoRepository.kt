package pe.edu.upeu.pharmamobil.data

import pe.edu.upeu.pharmamobil.domain.model.Producto

/**
 * Almacenamiento en memoria de productos. El id lo asigna el repositorio,
 * no la pantalla, para evitar identificadores duplicados.
 */
class ProductoRepository {

    private val productos = mutableListOf<Producto>()
    private var siguienteId = 1L

    fun registrar(producto: Producto): Producto {
        val guardado = producto.copy(id = siguienteId++)
        productos.add(guardado)
        return guardado
    }

    fun listar(): List<Producto> = productos.toList()
}