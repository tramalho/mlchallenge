package br.com.tramalho.mlchallenge.data.infra

import java.lang.StringBuilder

class Constants {
    companion object {
        val COLUMNS: Int = 2
        val EXTRA_DATA: String = "EXTRA_DATA"
        val ML_ENDPOINT: StringBuilder = StringBuilder("https://api.mercadolibre.com/sites/MLU/")
        const val LIMIT = 40
    }
}