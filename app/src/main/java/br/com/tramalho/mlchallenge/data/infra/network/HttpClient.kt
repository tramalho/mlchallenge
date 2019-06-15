package br.com.tramalho.mlchallenge.data.infra.network

import br.com.tramalho.mlchallenge.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class HttpClient {

    private val timeout: Long = 5

    fun build(baseUrl: String): Retrofit {
        return Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(logInterceptor())
            .build()
    }

    private fun logInterceptor(): OkHttpClient {

        val logging = HttpLoggingInterceptor()

        logging.level = levelLog()

        return OkHttpClient
            .Builder()
            .addInterceptor(logging)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .build()
    }

    private fun levelLog(): HttpLoggingInterceptor.Level {
        return when {
            BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }
    }
}