package com.poc.jsonplaceholderapisample.gateway

import com.poc.jsonplaceholderapisample.model.response.post_listing_response.PostResponse
import com.poc.jsonplaceholderapisample.model.response.user_detail_response.UserDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {
    @GET("posts")
    suspend fun getPostList(): Response<PostResponse>

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): Response<UserDetailResponse>
}