package com.example.flowdemo.work

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data

import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.flowdemo.DataStore.DataStoreSetting
import com.example.flowdemo.ui.MainActivity
import com.example.flowdemo.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.observeOn
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class UploadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    var context = applicationContext
    var DataStoreSetting: DataStoreSetting? = null

    companion object {
        const val key_worker = "key_worker"

    }

    private var mIsRandomGeneratorOn = false

    override fun doWork(): Result {
        try {
            val count = inputData.getInt(MainActivity.Key_count_value, 0)
            Thread {
                for (i in 0 until count) {
                    Log.i(
                        ContentValues.TAG,
                        "startRandomNumberGenerator: $i" + com.example.flowdemo.DataStore.DataStoreSetting.Preferencekey.name
                    )
                }
            }.start()

            val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentdate: String = time.format(Date())

            val output_data: Data = Data.Builder()
                .putString(key_worker, currentdate)
                .build()


            return Result.success(output_data)
        } catch (e: Exception) {
            return Result.failure()
        }

    }

    private fun startRandomNumberGenerator() {
        while (mIsRandomGeneratorOn) {
            try {
                Thread.sleep(1000)
                if (mIsRandomGeneratorOn) {

                }
            } catch (e: InterruptedException) {
                Log.i("service_demo_tag", "Thread Interrupted")
            }
        }
    }

}