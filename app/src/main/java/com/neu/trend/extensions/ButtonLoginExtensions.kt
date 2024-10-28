package com.neu.trend.extensions

import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.Button

fun Button.underlineText(){
    val content = SpannableString(this.text)
    content.setSpan(UnderlineSpan(), 0, this.text.length, 0)
    this.text = content
}