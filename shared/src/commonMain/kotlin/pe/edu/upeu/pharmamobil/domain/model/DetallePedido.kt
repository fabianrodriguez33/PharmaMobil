package pe.edu.upeu.pharmamobil.domain.model

data class DetallePedido(
    val producto: Producto,
    val cantidad: Int,
    val precioUnitario: Double
) {
    init {
        require(cantidad > 0) { "La cantidad debe ser mayor a 0" }
    }

    val subtotal: Double get() = cantidad * precioUnitario
}