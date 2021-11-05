package com.example.flowdemo.Util

class Generic<T>(var data: T) {
    fun setvalue(data: T) {
        this.data = data
    }

    fun getvalue(): T {
        return data
    }
}