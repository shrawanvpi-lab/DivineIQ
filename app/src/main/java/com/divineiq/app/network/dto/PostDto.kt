package com.divineiq.app.network.dto

import com.google.gson.annotations.SerializedName

/**
 * Wire model for a post entry returned by the remote content API. Posts are
 * mapped into quiz summaries in the repository layer.
 */
data class PostDto(
    @SerializedName("id") val id: Long,
    @SerializedName("userId") val userId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
)
