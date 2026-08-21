package pe.edu.upeu.pharmamobil.demo

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pe.edu.upeu.pharmamobil.data.ResultadoProductos
import pe.edu.upeu.pharmamobil.data.cargarProductos
import pe.edu.upeu.pharmamobil.data.observarEstados
import pe.edu.upeu.pharmamobil.data.observarProductos

fun main() = runBlocking {
    launch {
        observarEstados().collect { estado ->
            println(estado)
        }
    }

    launch {
        observarProductos().collect { productos ->
            println("Productos recibidos: ${productos.size}")
        }
    }

    launch {
        cargarProductos().collect { resultado ->
            when (resultado) {
                ResultadoProductos.Cargando -> {
                    println("Cargando productos...")
                }

                is ResultadoProductos.Exito -> {
                    println("Productos cargados: ${resultado.list}")
                }

                is ResultadoProductos.Error -> {
                    println("Error: ${resultado.msg}")
                }
            }
        }
    }
}