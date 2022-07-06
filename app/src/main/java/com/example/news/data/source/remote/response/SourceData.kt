package com.example.news.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SourceData(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String
) : Parcelable