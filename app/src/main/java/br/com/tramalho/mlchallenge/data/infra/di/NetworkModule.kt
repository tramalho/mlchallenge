package br.com.tramalho.mlchallenge.data.infra.di

import br.com.tramalho.mlchallenge.data.infra.network.HttpClient
import br.com.tramalho.mlchallenge.data.infra.network.ItemService
import br.com.tramalho.mlchallenge.data.infra.network.ServiceFactory
import org.koin.dsl.module

val networkModule = module {
    single { HttpClient().build(get()) }

    single { ServiceFactory(get()).create<ItemService>() }
}
