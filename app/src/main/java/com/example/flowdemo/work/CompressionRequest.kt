package com.example.flowdemo.work

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.flowdemo.ui.MainActivity
import java.lang.Exception

class CompressionRequest(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        try {
            val count = inputData.getInt(MainActivity.Key_count_value, 0)
            Thread {
                for (i in 0 until count) {
                    Log.i(ContentValues.TAG, "startRandomNumberGenerator CompressionRequest: $i")
                }
            }.start()

            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}