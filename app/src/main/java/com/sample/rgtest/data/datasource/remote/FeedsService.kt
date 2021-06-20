package com.sample.rgtest.data.datasource.remote


import com.sample.rgtest.data.modal.RssFeed
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Mukesh
 *
 * Api services
 */

interface FeedsService {
    @GET("feed/")
    suspend fun getAllFeeds(@Query("cat") cat: String) : Response<RssFeed>

}