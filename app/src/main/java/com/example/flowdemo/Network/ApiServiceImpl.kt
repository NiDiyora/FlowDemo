package com.example.flowdemo.Network

import com.example.flowdemo.model.Post
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private var apiService: ApiService) {

    suspend fun getpost():List<Post> = apiService.getpost()

}