package pe.edu.upeu.pharmamobil.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import pe.edu.upeu.pharmamobil.domain.model.Producto
import pe.edu.upeu.pharmamobil.domain.repository.ProductoRepository
import kotlin.random.Random

class ProductoRepositorioEnMemoria : ProductoRepository {

    private val productos = mutableListOf<Producto>()
    private var siguienteId = 1
    private val mutex = Mutex()

    override suspend fun registrar(producto: Producto): Producto {
        delay(Random.nextLong(300, 800))
        return mutex.withLock {
            val nuevo = producto.copy(id = siguienteId++)
            productos.add(nuevo)
            nuevo
        }
    }

    override suspend fun listar(): List<Producto> {
        delay(Random.nextLong(300, 800))
        return mutex.withLock { productos.toList() }
    }
}