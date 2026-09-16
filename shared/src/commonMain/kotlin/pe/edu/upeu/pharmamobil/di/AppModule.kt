package pe.edu.upeu.pharmamobil.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import pe.edu.upeu.pharmamobil.data.repository.ProductoRepositorioEnMemoria
import pe.edu.upeu.pharmamobil.domain.repository.ProductoRepository
import pe.edu.upeu.pharmamobil.domain.usecase.RegistrarProductoUseCase
import pe.edu.upeu.pharmamobil.presentation.producto.ProductoViewModel

val domainModule = module {
    single { RegistrarProductoUseCase(get()) }
}

val dataModule = module {
    single<ProductoRepository> { ProductoRepositorioEnMemoria() }
}

val presentationModule = module {
    factory { ProductoViewModel(get(), get()) }
}

expect val platformModule: org.koin.core.module.Module

fun initKoin(config: KoinAppDeclaration = {}) {
    startKoin {
        config()
        modules(domainModule, dataModule, presentationModule, platformModule)
    }
}