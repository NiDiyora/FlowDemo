package com.example.flowdemo.Util

import com.example.flowdemo.model.Post

sealed class ApiState {

    object Loading : ApiState()
    object Empty : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    class Success(val data: List<Post>) : ApiState()
}
