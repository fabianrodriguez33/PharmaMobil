package pe.edu.upeu.pharmamobil.di

import org.koin.dsl.module
import org.koin.core.context.startKoin

actual val platformModule = module { }

fun initKoinIos() {
    initKoin()
}