package com.android.aop.part2.locationsearch.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResultEntity(val fullAdress: String,
                              val name: String,
                              val locationLatLng: LocationLatLngEntity) : Parcelable