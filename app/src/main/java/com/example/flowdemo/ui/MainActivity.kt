package com.example.flowdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import androidx.work.WorkManager.getInstance
import com.droidnet.DroidListener
import com.droidnet.DroidNet
import com.example.flowdemo.R
import com.example.flowdemo.Util.ApiState
import com.example.flowdemo.Util.Day
import com.example.flowdemo.Util.Generic
import com.example.flowdemo.adapter.PostAdapter
import com.example.flowdemo.databinding.ActivityMainBinding
import com.example.flowdemo.model.Post
import com.example.flowdemo.viewmodel.MainViewModel
import com.example.flowdemo.work.CompressionRequest
import com.example.flowdemo.work.DownlodingWork
import com.example.flowdemo.work.UploadWorker
import com.example.flowdemo.work.filteringRequest
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.concurrent.TimeUnit
import kotlin.math.pow

fun gn() {

}

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DroidListener {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var postAdapter: PostAdapter
    var post: Post? = null
    lateinit var mDroidNet: DroidNet
    private val mainViewModel: MainViewModel by viewModels()

    var lamdas = { x: Double, y: Double ->
        x.pow(y)
    }
    val collectnum: Float by lazy {
        3.14f
    }
    var multilamda = {
        var a = 2 + 3
        Log.d("tag", "lambdas multi" + a)
    }
    val singlelambdas = { x: Int -> x * x }
    val singlelabdas: (Int) -> Int = { x -> x * x }
    val simplyfysingleparam: (Int) -> Int = { it * it }

    companion object {
        const val Key_count_value = "Key_count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDroidNet = DroidNet.getInstance();
        mDroidNet.addInternetConnectivityListener(this);
        var sp = ::setupworker
        sp()

        multilamda()
        //  setPeriodicWorkRequest()
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        postAdapter = PostAdapter(ArrayList())
        BottomSheetBehavior.from(activityMainBinding.sheet).apply {
            peekHeight = 50
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        activityMainBinding.txt.append("\n $collectnum")

        calculate(3.0, 4.0, lamdas)
        var list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        var list1 = list.filter { a: Int -> a % 2 == 0 }
        var list2 = list.filter { it % 2 != 0 }

        activityMainBinding.txt.append("\n" + list1 + "\n" + list2)
//        val power = ::power
//        activityMainBinding.txt.append("\n "+power(3.0,3.0))


        lifecycleScope.launchWhenStarted {
            mainViewModel._postStateflow.collect {
                when (it) {
                    is ApiState.Loading -> {
                        activityMainBinding.recyclerview.isVisible = false
                        activityMainBinding.progress.isVisible = true
                    }
                    is ApiState.Failure -> {
                        activityMainBinding.recyclerview.isVisible = false
                        activityMainBinding.progress.isVisible = false
                        Log.d("Tag", "On Create: ${it.msg}")
                    }
                    is ApiState.Success -> {
                        activityMainBinding.recyclerview.isVisible = true
                        activityMainBinding.progress.isVisible = false
                        postAdapter.setData(it.data)

                    }
                }
            }
        }
        mainViewModel.getpost()

        activityMainBinding.recyclerview.setHasFixedSize(true)
        activityMainBinding.recyclerview.adapter = postAdapter
        Log.d("this", "Hello String".formettedstring())
        Log.d("this", "Hello String".uppercase())
        Log.d("this", "Hello String1".lowercase())


        if (!TextUtils.isEmpty("this is nirgav")) {
            mainViewModel.writeTolocal("this is the testing of DataStore")
            Log.i("text", "this is the testing of DataStore")

        } else {
            Toast.makeText(this, "empty", Toast.LENGTH_LONG).show()
        }

        lifecycleScope.launchWhenCreated {
            mainViewModel.readtolocal.collect {
                Log.d("some message", it)
                activityMainBinding.txt.append(it)
            }
        }
        var day = Day.FRIDAY
        activityMainBinding.txt.append("\n" + day.printdayformetted())
        for (i in Day.values()) {
            activityMainBinding.txt.append("\n" + i.toString())
        }

        var studentd = mutableMapOf<Int, String>()
        studentd.apply {
            put(1, "nirgav")
            put(2, "vishal")
            put(3, "Greejesh")
            put(4, "rutik")
            put(5, "Himanshu")
        }

        studentd[5] = "kartik"
        studentd[6] = "himanshu"

        for (i in studentd.values) {
            activityMainBinding.txt.append("\n $i")
        }

        var student = mapOf<Int, String>(1 to "nirgav", 2 to "vishal", 3 to "kartik")
        for (i in student.values)
            activityMainBinding.txt.append("\n $i")

        post?.apply {
            body = "this is the body"
            id = 2
        }

        post?.let {
            it.body = "this is body"
            it.id = 3
        }
        post?.let {
            with(post) {
                this?.body = "nulleble"
                this?.id = 4
            }

        }
        post?.run {
            body = "no body found"
            id = 3
        }
        var generic = Generic(5.555)
        generic.getvalue()

        add(1, 2, 3, 4, 5, 6, 7, 8, 9)


    }

    fun add(vararg values: Int) {
        var sum = 0
        for (i in values) {
            sum += i
        }
        Log.d("sum:-", "sum is = $sum")
    }

    fun setupworker() {
        val workManager = getInstance(applicationContext)

        val data: Data = Data.Builder()
            .putInt(Key_count_value, 3000)
            .build()
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val uplodeworker = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraints)
            .setInputData(data).build()
        val filterrequest = OneTimeWorkRequest.Builder(filteringRequest::class.java)
            .setConstraints(constraints)
            .setInputData(data).build()
        val compressionRequest = OneTimeWorkRequest.Builder(CompressionRequest::class.java)
            .setConstraints(constraints)
            .setInputData(data).build()
        val downloadwork = OneTimeWorkRequest.Builder(DownlodingWork::class.java)
            .setConstraints(constraints)
            .setInputData(data).build()

        val paralleWorks = mutableListOf<OneTimeWorkRequest>()
        paralleWorks.add(downloadwork)
        paralleWorks.add(filterrequest)
        workManager.beginWith(paralleWorks)
            .then(compressionRequest)
            .then(uplodeworker)
            .enqueue()
        workManager.getWorkInfoByIdLiveData(uplodeworker.id)
            .observe(this, Observer {
                //  activityMainBinding.txt.text = it.state.name
                if (it.state.isFinished) {
                    val data: Data = it.outputData
                    val message: String? = data.getString(UploadWorker.key_worker)
                    //  Toast.makeText(this, message, Toast.LENGTH_LONG).show()

                }
            })

    }

    fun setPeriodicWorkRequest() {
        val periodicWorkRequest = PeriodicWorkRequest
            .Builder(DownlodingWork::class.java, 16, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(periodicWorkRequest)
    }

    override fun onInternetConnectivityChanged(isConnected: Boolean) {
        if (isConnected) {
            mainViewModel.getpost()
        } else {
            Toast.makeText(this, "not connected internet", Toast.LENGTH_SHORT).show()
            Log.i("notconnect", "internet not connected")

        }
    }

//
//    fun power(a: Double, b: Double): Double {
//        return a.pow(b)
//    }

    fun calculate(a: Double, b: Double, power: (Double, Double) -> Double) {
        var result = power(a, b)
        Toast.makeText(this, "charecter", Toast.LENGTH_LONG)
        activityMainBinding.txt.append("\n $result")
    }

    fun String.formettedstring(): String {
        return "----------------\n$this\n---------------------"
    }

}