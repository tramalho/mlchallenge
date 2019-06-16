package br.com.tramalho.mlchallenge.data.infra.di

import br.com.tramalho.mlchallenge.data.infra.Constants
import org.koin.dsl.module

val urlModule = module(override = true) {
    single { StringBuilder(Constants.ML_ENDPOINT) }
}