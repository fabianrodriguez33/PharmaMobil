package pe.edu.upeu.pharmamobil.presentation.producto

import pe.edu.upeu.pharmamobil.domain.model.Producto

sealed interface ProductoListaFase {
    data object Cargando : ProductoListaFase
    data object SinProductos : ProductoListaFase
    data class ConProductos(val productos: List<Producto>) : ProductoListaFase
    data class Error(val mensaje: String) : ProductoListaFase
}

data class ProductoFormularioState(
    val nombre: String = "",
    val categoria: String = "",
    val precio: String = "",
    val stock: String = "",
    val errorNombre: String? = null,
    val errorPrecio: String? = null,
    val errorStock: String? = null,
    val guardando: Boolean = false
)

data class ProductoUiState(
    val fase: ProductoListaFase = ProductoListaFase.Cargando,
    val formulario: ProductoFormularioState = ProductoFormularioState()
)