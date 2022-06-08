package com.siloka.client.views.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Topic(
    var id: Int = 0,
    var question: String,
): Parcelable
