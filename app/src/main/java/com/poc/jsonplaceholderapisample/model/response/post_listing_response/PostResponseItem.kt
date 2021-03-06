package com.poc.jsonplaceholderapisample.model.response.post_listing_response


import com.google.gson.annotations.SerializedName

data class PostResponseItem(
    @SerializedName("body")
    val body: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int
)