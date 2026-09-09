package pe.edu.upeu.pharmamobil.domain.model

data class Producto(
    val id: Int = 0,
    val nombre: String,
    val categoria: String,
    val precio: Double,
    val stock: Int
) {
    companion object {
        const val STOCK_MINIMO = 10
    }

    fun requiereReposicion(): Boolean = stock < STOCK_MINIMO
}