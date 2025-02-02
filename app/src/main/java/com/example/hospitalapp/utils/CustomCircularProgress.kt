package com.example.hospitalapp.utils

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorInt
import com.example.hospitalapp.R

class CustomCircularProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var rotationAnimator: ValueAnimator? = null
    private var rotationAngle: Float = 0f

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 12f  // Adjust width for smoothness
        color = PROGRESS_COLOR
        strokeCap = Paint.Cap.ROUND  // Rounded edges for smooth stroke ends
    }

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 12f  // Slightly thicker for a prominent background
        color = BACKGROUND_COLOR
        strokeCap = Paint.Cap.ROUND
    }

    private var radius: Float = 0f

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
        // Adjust radius to fit the view
        radius = Math.min(w, h) / 2f - progressPaint.strokeWidth
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw background circle
        canvas.drawCircle(
            width / 2f, height / 2f, radius, backgroundPaint
        )

        // Draw rotating progress arc (indeterminate)
        val sweepAngle = 270f  // Arc for indeterminate progress (you can adjust this value)
        canvas.drawArc(
            width / 2f - radius, height / 2f - radius, width / 2f + radius, height / 2f + radius,
            rotationAngle, sweepAngle, false, progressPaint
        )
    }

    fun startIndeterminateAnimation() {
        if (rotationAnimator == null) {
            rotationAnimator = ValueAnimator.ofFloat(0f, 360f).apply {
                duration = ANIMATION_DURATION  // Adjust duration for smoother animation
                interpolator = LinearInterpolator()  // Linear smooth rotation
                repeatCount = ValueAnimator.INFINITE  // Infinite loop until stopped
                addUpdateListener { animation ->
                    rotationAngle = animation.animatedValue as Float
                    invalidate()  // Redraw view with updated angle
                }
                start()  // Start the animation
            }
        }
    }

    fun stopIndeterminateAnimation() {
        rotationAnimator?.cancel()  // Stop the animation when the data is ready
        rotationAnimator = null
        rotationAngle = 0f  // Reset the rotation angle
        invalidate()  // Redraw view to reset the progress
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
        private const val ANIMATION_DURATION = 1200L  // Adjust to control rotation speed
        private val PROGRESS_COLOR = Color.parseColor("#22C7B8")  // Teal color for progress
        private val BACKGROUND_COLOR = Color.parseColor("#1522C7B8")  // Subtle background color
    }
}
