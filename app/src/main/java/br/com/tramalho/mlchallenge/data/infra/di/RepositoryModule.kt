package br.com.tramalho.mlchallenge.data.infra.di

import br.com.tramalho.mlchallenge.data.repository.ItemRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { ItemRepository(get()) }
}