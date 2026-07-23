package com.divineiq.app.network.dto

import com.google.gson.annotations.SerializedName

/**
 * Wire model for a photo entry returned by the remote content API.
 */
data class PhotoDto(
    @SerializedName("id") val id: Long,
    @SerializedName("albumId") val albumId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String
)
