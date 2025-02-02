package com.example.hospitalapp.utils

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.annotation.ColorInt
import com.example.hospitalapp.R


class CustomProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progressRect = RectF()
    private var progressAnimator: ValueAnimator? = null

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = PROGRESS_COLOR
    }

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = BACKGROUND_COLOR
    }

    private var progress: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressView)
            progressPaint.color = typedArray.getColor(
                R.styleable.CustomProgressView_progressColor,
                PROGRESS_COLOR
            )
            backgroundPaint.color = typedArray.getColor(
                R.styleable.CustomProgressView_backgroundColor,
                BACKGROUND_COLOR
            )
            typedArray.recycle()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        progressRect = RectF(0f, 0f, w.toFloat(), h.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw background
        canvas.drawRoundRect(
            progressRect,
            height / 2f,
            height / 2f,
            backgroundPaint
        )

        // Draw progress
        val progressWidth = progressRect.width() * (progress / 100f)
        val progressBar = RectF(0f, 0f, progressWidth, height.toFloat())
        canvas.drawRoundRect(
            progressBar,
            height / 2f,
            height / 2f,
            progressPaint
        )
    }

    fun setProgress(newProgress: Float, animate: Boolean = true) {
        if (animate) {
            progressAnimator?.cancel()

            progressAnimator = ValueAnimator.ofFloat(progress, newProgress).apply {
                duration = ANIMATION_DURATION
                interpolator = DecelerateInterpolator()
                addUpdateListener { animation ->
                    progress = animation.animatedValue as Float
                }
                start()
            }
        } else {
            progress = newProgress
        }
    }

    fun setProgressColor(@ColorInt color: Int) {
        progressPaint.color = color
        invalidate()
    }

    override fun setBackgroundColor(@ColorInt color: Int) {
        backgroundPaint.color = color
        invalidate()
    }

    companion object {
        private const val ANIMATION_DURATION = 300L
        private val PROGRESS_COLOR = Color.parseColor("#22C7B8")  // Teal color
        private val BACKGROUND_COLOR = Color.parseColor("#1522C7B8")  // Semi-transparent teal
    }
}