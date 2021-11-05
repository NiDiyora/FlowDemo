package com.example.flowdemo.Util

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.flowdemo.R
import com.google.android.material.textfield.TextInputEditText

class CustomTextView : androidx.appcompat.widget.AppCompatTextView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()

    }

    constructor(context: Context, attrs: AttributeSet, defaultstyleAttr: Int) : super(
        context,
        attrs,
        defaultstyleAttr
    ) {
        init()

    }

    fun init() {
        if (!isInEditMode) {
            val typeface = Typeface.createFromAsset(context.assets, "fonts/opensance.ttf")
            setTypeface(typeface)

        }
    }
}