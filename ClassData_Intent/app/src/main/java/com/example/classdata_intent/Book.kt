package com.example.classdata_intent

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book(val id: Int, val name: String, val price: Int)
    : Parcelable
{

    override fun toString(): String {
        return "$id => $name : $price"
    }
}