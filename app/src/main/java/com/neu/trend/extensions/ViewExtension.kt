package com.neu.trend.extensions

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat


fun View.drawable(@DrawableRes drawableResource: Int): Drawable? =
        ContextCompat.getDrawable(context, drawableResource)

fun View.color(@ColorRes color: Int) = ContextCompat.getColor(context, color)
