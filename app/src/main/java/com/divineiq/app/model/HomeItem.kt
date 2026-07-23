package com.divineiq.app.model

/**
 * A single recommended learning card shown on the Home screen.
 */
data class HomeItem(
    val id: Long,
    val title: String,
    val subtitle: String,
    val category: String,
    val imageUrl: String
)
