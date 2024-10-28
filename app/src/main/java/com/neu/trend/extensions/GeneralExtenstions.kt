package com.neu.trend.extensions

import android.content.Intent
import android.view.ViewGroup
import android.widget.ImageView
import com.neu.trend.managers.GlideInstance
import java.io.File
import java.io.InputStream
import java.util.*
import kotlin.reflect.KProperty1


val millisToReadableTime: (Long) -> String = { millis ->
    val allSeconds = Math.round(millis / 1000.toDouble())
    val seconds = allSeconds % 60
    val minutes = ((allSeconds - seconds) / 60).toInt()
    String.format(Locale.US, "%02d:%02d", minutes, seconds)
}

fun <E> Iterable<E>.updated(index: Int, elem: E) =
    mapIndexed { i, existing -> if (i == index) elem else existing }

fun ImageView.loadImage(url: String?) = GlideInstance.getGlide(context)
    .load(url)
    .into(this)

fun InputStream.toFile(path: String) = File(path).outputStream().use { this.copyTo(it) }

fun <T> List<T>.getListOfValue(value: String): List<String> {
    return this.map { value }
}

fun ViewGroup.addMargin(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
    val p = this.layoutParams as ViewGroup.MarginLayoutParams
    p.setMargins(left, top, right, bottom)
    this.requestLayout()
}

fun Intent.getExtraData(key: String): String? {
    return extras?.getString(key)
}

inline fun <E> MutableList<E>.listOfField(property: KProperty1<E, Int>): MutableList<Int> {
    val yy = ArrayList<Int>()
    this.forEach { t: E ->
        yy.add(property.get(t))
    }
    return yy
}
