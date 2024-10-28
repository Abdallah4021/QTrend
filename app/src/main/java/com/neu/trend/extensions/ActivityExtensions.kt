package com.neu.trend.extensions

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

fun Activity.drawable(@DrawableRes drawableResource: Int): Drawable? =
    ContextCompat.getDrawable(this, drawableResource)

fun Activity.color(@ColorRes color: Int) = ContextCompat.getColor(this, color)

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

fun Fragment.drawable(@DrawableRes drawableResource: Int): Drawable? =
        ContextCompat.getDrawable(requireContext(), drawableResource)

fun Fragment.color(@ColorRes color: Int) = ContextCompat.getColor(requireContext(), color)






