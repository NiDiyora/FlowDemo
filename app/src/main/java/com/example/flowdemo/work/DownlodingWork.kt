package com.example.flowdemo.work

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.flowdemo.ui.MainActivity
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class DownlodingWork(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        try {
            val count = inputData.getInt(MainActivity.Key_count_value, 0)
            Thread {
              //  this is the thread maintain
                for (i in 0 until 2000) {
                    Log.i(ContentValues.TAG, "startRandomNumberGenerator downlodeWork: $i")
                }
            }.start()
            val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentdate: String = time.format(Date())

            Log.i(ContentValues.TAG, "startRandomNumberGenerator Currenttime: $currentdate")

            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }

    }

}