package com.example.flowdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowdemo.DataStore.DataStoreSetting
import com.example.flowdemo.Network.MainRepository
import com.example.flowdemo.Util.ApiState
import com.example.flowdemo.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository, private val dataStoreSetting: DataStoreSetting,

    ) : ViewModel() {

    private val postStateFlow: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    val _postStateflow: StateFlow<ApiState> = postStateFlow

    fun getpost() = viewModelScope.launch {
        postStateFlow.value = ApiState.Loading
        mainRepository.getpost()
            .catch { e ->
                postStateFlow.value = ApiState.Failure(e)
            }.collect {
                postStateFlow.value = ApiState.Success(it)
            }
    }

    fun writeTolocal(name: String) = viewModelScope.launch {
        dataStoreSetting.writeToLocal(name)
    }

    var readtolocal = dataStoreSetting.readToLocal
}