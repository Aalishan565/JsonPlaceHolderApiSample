package com.poc.jsonplaceholderapisample.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poc.jsonplaceholderapisample.gateway.Resource
import com.poc.jsonplaceholderapisample.model.repository.Repository
import com.poc.jsonplaceholderapisample.model.response.post_listing_response.PostResponse
import com.poc.jsonplaceholderapisample.model.response.user_detail_response.UserDetailResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {
    var mutablePostLiveData: MutableLiveData<Resource<PostResponse>> = MutableLiveData()
    var mutableUserDetailLiveData: MutableLiveData<Resource<UserDetailResponse>> = MutableLiveData()

    fun callUserListApi() {
        viewModelScope.launch {
            try {
                mutablePostLiveData.postValue(Resource.Loading())
                val response = repository.getPostList()
                mutablePostLiveData.postValue(handleResponse(response))
            } catch (ex: Exception) {
                handleErrorResponse(ex)
            }
        }
    }

    fun callUser(userId: Int) {
        viewModelScope.launch {
            try {
                mutableUserDetailLiveData.postValue(Resource.Loading())
                val response = repository.getUser(userId)
                mutableUserDetailLiveData.postValue(handleUserDetailResponse(response))
            } catch (ex: Exception) {
                handleErrorResponse(ex)
            }
        }
    }

    private fun handleErrorResponse(ex: Exception): Resource.Error<Nothing>? {
        return ex.message?.let { Resource.Error(it) }

    }

    private fun handleResponse(response: Response<PostResponse>): Resource<PostResponse>? {
        if (response.isSuccessful) {
            return response.body()?.let { Resource.Success(it) }!!
        }
        return Resource.Error(response.message())
    }

    private fun handleUserDetailResponse(response: Response<UserDetailResponse>): Resource<UserDetailResponse>? {
        if (response.isSuccessful) {
            return response.body()?.let { Resource.Success(it) }!!
        }
        return Resource.Error(response.message())
    }
}