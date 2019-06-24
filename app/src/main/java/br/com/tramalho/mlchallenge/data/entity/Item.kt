package br.com.tramalho.mlchallenge.data.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemResult(val query: String, val paging: Paging, var results: List<ItemSearch>) : Parcelable

@Parcelize
data class ItemSearch(
    val id: String,
    val title: String,
    val thumbnail: String,
    val installments: Installments?,
    val price: Double
) : Parcelable

@Parcelize
data class Installments(val quantity: Int, val amount: Double) : Parcelable

@Parcelize
data class Paging(val total: Int, val offset: Int, val limit: Int) : Parcelable

data class ItemDetail(
    val title: String,
    val price: Double,
    val pictures: List<Picture>,
    @field:Json(name = "sold_quantity")
    val soldQuantity: Int
)

data class Picture(val url: String)

data class ItemDescription(@field:Json(name = "plain_text") val description: String)

data class ItemDetailResult(val itemDetail: ItemDetail, val description: String)