package com.example.flowdemo.Network

import com.example.flowdemo.model.Post
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getpost():List<Post>
}