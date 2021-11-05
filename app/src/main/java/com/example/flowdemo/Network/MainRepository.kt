package com.example.flowdemo.Network

import com.example.flowdemo.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiServiceImpl: ApiServiceImpl) {

    fun getpost(): Flow<List<Post>> = flow {
        emit(apiServiceImpl.getpost())
    }.flowOn(Dispatchers.IO)

}