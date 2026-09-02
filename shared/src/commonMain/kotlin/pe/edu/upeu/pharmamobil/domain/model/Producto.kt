package pe.edu.upeu.pharmamobil.domain.model

data class Producto(
    val id: Long,
    val nombre: String,
    val precio: Double,
    val stock: Int,
    val activo: Boolean = true
){
    init {
        require(value = nombre.isNotBlank()){
            "El nombre no puede estar vacío"
        }

        require(value = precio>0){
            "El precio debe ser mayor que 0"
        }

        require(value = stock>=0){
            "El stock no puede ser negativo"
        }
    }
    fun verificarStock(cantidad: Int): Boolean{
        return stock >= cantidad
    }

    fun estadoDisponible(): Boolean{
        return stock > 0
    }

    fun valorInventario(): Double{
        return precio * stock
    }

    fun disminuirStock(cantidad: Int): Producto{
        require(value = cantidad > 0){
            "La cantidad debe ser mayor cero"
        }
        require(value = verificarStock(cantidad)){
            "Stock inssuficiente"
        }
        return copy(
            stock = stock - cantidad
        )
    }
}

val productosDemo = listOf(
    Producto(
        id = 1,
        nombre = "Paracetamol",
        precio = 15.50,
        stock = 100,
        activo = true
    ),
    Producto(
        id = 2,
        nombre = "Ibuprofeno",
        precio = 18.90,
        stock = 50,
        activo = true
    ),
    Producto(
        id = 3,
        nombre = "Amoxicilina",
        precio = 25.00,
        stock = 5,
        activo = true
    ),
    Producto(
        id = 4,
        nombre = "Loratadina",
        precio = 12.50,
        stock = 0,
        activo = false
    ),
    Producto(
        id = 5,
        nombre = "Diclofenaco",
        precio = 20.00,
        stock = 3,
        activo = true
    )
)
