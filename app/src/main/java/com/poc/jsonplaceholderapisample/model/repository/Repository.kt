package com.poc.jsonplaceholderapisample.model.repository

import com.poc.jsonplaceholderapisample.gateway.RetrofitService
import com.poc.jsonplaceholderapisample.model.response.post_listing_response.PostResponse
import com.poc.jsonplaceholderapisample.model.response.user_detail_response.UserDetailResponse
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val retrofit: RetrofitService) {

    suspend fun getPostList(): Response<PostResponse> = retrofit.getPostList()
    suspend fun getUser(userId: Int): Response<UserDetailResponse> = retrofit.getUser(userId)

}