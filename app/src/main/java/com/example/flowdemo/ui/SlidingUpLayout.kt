package com.example.flowdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.flowdemo.R
import com.example.flowdemo.databinding.ActivitySlidingUpLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class SlidingUpLayout : AppCompatActivity() {
    lateinit var binding: ActivitySlidingUpLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sliding_up_layout)
        BottomSheetBehavior.from(binding.sheet).apply {
            peekHeight = 50
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }


    }
}