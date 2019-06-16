package br.com.tramalho.mlchallenge.data.infra.network

import retrofit2.Retrofit

class ServiceFactory(val retrofit: Retrofit){

    inline fun <reified T> create() : T = retrofit.create(T::class.java)
}