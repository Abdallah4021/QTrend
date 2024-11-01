package com.neu.trend.widget

import android.animation.AnimatorSet
import android.animation.ObjectAnimator.ofInt
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Keep
import androidx.core.content.res.ResourcesCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.neu.trend.R


class BottomNavigationViewIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {


    private val targetId: Int
    private val backgroundDrawable: Drawable
    private var target: BottomNavigationMenuView? = null
    private var rect = Rect()
    private var animator: AnimatorSet? = null
    private var index = 1

    init {
        if (attrs == null) {
            targetId = NO_ID
            backgroundDrawable = ColorDrawable(Color.TRANSPARENT)
        } else {
            with(context.obtainStyledAttributes(attrs, R.styleable.BottomNavigationViewIndicator)) {
                targetId = getResourceId(
                    R.styleable.BottomNavigationViewIndicator_targetBottomNavigation,
                    NO_ID
                )
                val clipableId = getResourceId(
                    R.styleable.BottomNavigationViewIndicator_clipableBackground,
                    NO_ID
                )
                backgroundDrawable = if (clipableId != NO_ID) {
                    ColorDrawable(ResourcesCompat.getColor(resources, clipableId, null))
                        ?: ColorDrawable(Color.TRANSPARENT)
                } else {
                    ColorDrawable(
                        getColor(
                            R.styleable.BottomNavigationViewIndicator_clipableBackground,
                            Color.TRANSPARENT
                        )
                    )
                }
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.clipRect(rect)
        backgroundDrawable.draw(canvas)
    }

    private fun updateRect(rect: Rect) {
        this.rect = rect
        postInvalidate()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (targetId == NO_ID) return attachedError("invalid target id $targetId, did you set the app:targetBottomNavigation attribute?")
        val parentView = parent as? View
            ?: return attachedError("Impossible to find the view using $parent")
        val child = parentView.findViewById<View?>(targetId)
        if (child !is ListenableBottomNavigationView) return attachedError("Invalid view $child, the app:targetBottomNavigation has to be n ListenableBottomNavigationView")
        for (i in 0 until child.childCount) {
            val subView = child.getChildAt(i)
            if (subView is BottomNavigationMenuView) target = subView
        }
        elevation = child.elevation
        child.addOnNavigationItemSelectedListener { updateRectByIndex(it, true) }
        post { updateRectByIndex(index, false) }
    }

    private fun attachedError(message: String) {

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        target = null
    }

    private fun startUpdateRectAnimation(rect: Rect) {
        animator?.cancel()
        animator = AnimatorSet().also {
            it.playTogether(
                ofInt(this, "rectLeft", this.rect.left, rect.left),
                ofInt(this, "rectRight", this.rect.right, rect.right),
                ofInt(this, "rectTop", this.rect.top, rect.top),
                ofInt(this, "rectBottom", this.rect.bottom, rect.bottom)
            )
            it.interpolator = FastOutSlowInInterpolator()
            it.duration = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
            it.start()
        }
    }

    private fun updateRectByIndex(index: Int, animated: Boolean) {
        this.index = index
        target?.apply {
            if (childCount < 1 || index >= childCount) return
            val reference = getChildAt(index)

            val start = reference.left + left
            val end = reference.right + left

            backgroundDrawable.setBounds(left, top, right, bottom)
            val newRect = Rect(start, 0, end, height)
            if (animated) startUpdateRectAnimation(newRect) else updateRect(newRect)
        }
    }

    fun updateIndicatorByIndex(index: Int) {
        updateRectByIndex(index, false)
    }

    @Keep
    fun setRectLeft(left: Int) = updateRect(rect.apply { this.left = left })

    @Keep
    fun setRectRight(right: Int) = updateRect(rect.apply { this.right = right })

    @Keep
    fun setRectTop(top: Int) = updateRect(rect.apply { this.top = top })

    @Keep
    fun setRectBottom(bottom: Int) = updateRect(rect.apply { this.bottom = bottom })
}