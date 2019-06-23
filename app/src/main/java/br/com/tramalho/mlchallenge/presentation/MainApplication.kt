package br.com.tramalho.mlchallenge.presentation

import android.app.Application
import br.com.tramalho.mlchallenge.data.infra.di.networkModule
import br.com.tramalho.mlchallenge.data.infra.di.presentationModule
import br.com.tramalho.mlchallenge.data.infra.di.repositoryModule
import br.com.tramalho.mlchallenge.data.infra.di.urlModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // loga os elementos injetados
            androidLogger()
            androidContext(this@MainApplication)
            // adiciona a lista de modulos com os objetos injetaveis
            modules(arrayListOf(urlModule, networkModule, presentationModule, repositoryModule))
        }
    }
}