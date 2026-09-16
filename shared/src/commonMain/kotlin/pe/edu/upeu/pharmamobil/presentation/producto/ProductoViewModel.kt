package pe.edu.upeu.pharmamobil.presentation.producto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pe.edu.upeu.pharmamobil.domain.repository.ProductoRepository
import pe.edu.upeu.pharmamobil.domain.usecase.RegistrarProductoUseCase

class ProductoViewModel(
    private val registrarProductoUseCase: RegistrarProductoUseCase,
    private val productoRepository: ProductoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductoUiState())
    val uiState: StateFlow<ProductoUiState> = _uiState.asStateFlow()

    init {
        cargarProductos()
    }

    fun cargarProductos() {
        _uiState.update { it.copy(fase = ProductoListaFase.Cargando) }
        viewModelScope.launch {
            try {
                val productos = productoRepository.listar()
                _uiState.update {
                    it.copy(
                        fase = if (productos.isEmpty()) ProductoListaFase.SinProductos
                        else ProductoListaFase.ConProductos(productos)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(fase = ProductoListaFase.Error(e.message ?: "Error al cargar")) }
            }
        }
    }

    fun onNombreChange(v: String) = _uiState.update { it.copy(formulario = it.formulario.copy(nombre = v, errorNombre = null)) }
    fun onCategoriaChange(v: String) = _uiState.update { it.copy(formulario = it.formulario.copy(categoria = v)) }
    fun onPrecioChange(v: String) = _uiState.update { it.copy(formulario = it.formulario.copy(precio = v, errorPrecio = null)) }
    fun onStockChange(v: String) = _uiState.update { it.copy(formulario = it.formulario.copy(stock = v, errorStock = null)) }

    fun registrarProducto() {
        val f = _uiState.value.formulario
        _uiState.update { it.copy(formulario = f.copy(guardando = true)) }
        viewModelScope.launch {
            val resultado = registrarProductoUseCase(
                nombre = f.nombre,
                categoria = f.categoria,
                precio = f.precio.toDoubleOrNull() ?: -1.0,
                stock = f.stock.toIntOrNull() ?: -1
            )
            resultado.fold(
                onSuccess = {
                    _uiState.update { it.copy(formulario = ProductoFormularioState()) }
                    cargarProductos()
                },
                onFailure = { e ->
                    _uiState.update {
                        it.copy(formulario = f.copy(
                            guardando = false,
                            errorNombre = if (e.message?.contains("nombre") == true) e.message else null,
                            errorPrecio = if (e.message?.contains("precio") == true) e.message else null,
                            errorStock = if (e.message?.contains("stock") == true) e.message else null
                        ))
                    }
                }
            )
        }
    }
}

private fun <T> MutableStateFlow<T>.update(block: (T) -> T) {
    this.value = block(this.value)
}