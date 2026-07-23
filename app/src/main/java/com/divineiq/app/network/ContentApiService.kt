package com.divineiq.app.network

import com.divineiq.app.network.dto.PhotoDto
import com.divineiq.app.network.dto.PostDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit service describing the remote endpoints DivineIQ pulls learning
 * content from.
 */
interface ContentApiService {

    @GET("photos")
    suspend fun getPhotos(@Query("_limit") limit: Int = 20): List<PhotoDto>

    @GET("posts")
    suspend fun getPosts(@Query("_limit") limit: Int = 20): List<PostDto>
}
