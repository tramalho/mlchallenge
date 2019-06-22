package br.com.tramalho.mlchallenge.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemResult(val query: String, val paging: Paging, var results: List<ItemSearch>) : Parcelable

@Parcelize
data class ItemSearch(val id: String, val title: String, val thumbnail: String, val installments: Installments) :
    Parcelable

@Parcelize
data class Installments(val quantity: Int, val amount: Double) : Parcelable

@Parcelize
data class Paging(val total: Int, val offset: Int, val limit: Int) : Parcelable
