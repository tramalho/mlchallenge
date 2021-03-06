package br.com.tramalho.mlchallenge.data.infra.di

import br.com.tramalho.mlchallenge.presentation.ItemDetaislViewModel
import br.com.tramalho.mlchallenge.presentation.ListViewModel
import br.com.tramalho.mlchallenge.presentation.SearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { ListViewModel(get()) }
    viewModel { ItemDetaislViewModel(get(), androidApplication()) }

}

