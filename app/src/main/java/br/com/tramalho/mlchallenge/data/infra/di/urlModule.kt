package br.com.tramalho.mlchallenge.data.infra.di

import br.com.tramalho.mlchallenge.data.infra.Constants
import org.koin.dsl.module

/**
 * Modulo para injecao do HOST que sera utilizado pelo HTTP Client
 * O mesmo sera sobreescrito para fins de testes
 */
val urlModule = module(override = true) {
    single { StringBuilder(Constants.ML_HOST) }
}