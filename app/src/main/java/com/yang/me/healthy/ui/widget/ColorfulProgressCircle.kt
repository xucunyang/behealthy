package com.yang.me.healthy.ui.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.yang.me.healthy.R
import timber.log.Timber
import kotlin.math.cos
import kotlin.math.sin

class ColorfulProgressCircle(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        const val TAG = "ColorfulProgressCircle"

        /**
         * 单个圆弧的宽度与整体款的比
         */
        const val ARC_WIDTH_SCALE = 0.12

        const val CIRCLE_SPACE_SCALE = 0.01

        const val SHADOW_DEGREE_OFFSET = 5

        /**
         * 绘制圆弧时的开始位置，rect的上中位置开始
         */
        const val START_ANGLE = -90f
    }

    var animateDuration = 1800 //ms

    /**
     * 目标角度
     */
    var outDestDegree: Float = 0f
    var midDestDegree: Float = 0f
    var innerDestDegree: Float = 0f

    private val animate: Boolean

    private val outCircleStartColor: Int
    private val outCircleEndColor: Int
    private val outCircleColorList: IntArray

    private val midCircleStartColor: Int
    private val midCircleEndColor: Int
    private val midCircleColorList: IntArray

    private val innerCircleStartColor: Int
    private val innerCircleEndColor: Int
    private val innerCircleColorList: IntArray

    private var shadowColorArray: IntArray

    private val circleRect: RectF = RectF()

    private var viewSize: Float = 0f

    private val mPaint: Paint

    private var circleSpace: Float = 0.0f

    private val rotateMatrix = Matrix()

    private var arcWidth: Float = 0f

    private val startEndCirclePaint = Paint()

    private val shadowPaint = Paint()

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ColorfulProgressCircle)

        animate = typedArray.getBoolean(R.styleable.ColorfulProgressCircle_animate, false)

        // out circle
        outCircleStartColor = typedArray.getColor(
            R.styleable.ColorfulProgressCircle_outCircleStartColor,
            resources.getColor(R.color.out_circle_start)
        )
        outCircleEndColor = typedArray.getColor(
            R.styleable.ColorfulProgressCircle_outCircleEndColor,
            resources.getColor(R.color.out_much_circle_end)
        )
        outCircleColorList = intArrayOf(outCircleStartColor, outCircleEndColor)

        //mid circle
        midCircleStartColor = typedArray.getColor(
            R.styleable.ColorfulProgressCircle_middleCircleStartColor,
            resources.getColor(R.color.mid_circle_start)
        )
        midCircleEndColor = typedArray.getColor(
            R.styleable.ColorfulProgressCircle_middleCircleEndColor,
            resources.getColor(R.color.mid_circle_end)
        )
        midCircleColorList = intArrayOf(midCircleStartColor, midCircleEndColor)

        //inner circle
        innerCircleStartColor = typedArray.getColor(
            R.styleable.ColorfulProgressCircle_innerCircleStartColor,
            resources.getColor(R.color.inner_circle_start)
        )
        innerCircleEndColor = typedArray.getColor(
            R.styleable.ColorfulProgressCircle_innerCircleEndColor,
            resources.getColor(R.color.inner_circle_end)
        )
        innerCircleColorList = intArrayOf(innerCircleStartColor, innerCircleEndColor)

        typedArray.recycle()

        mPaint = Paint()
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true

        startEndCirclePaint.style = Paint.Style.FILL

        shadowColorArray = intArrayOf(
            resources.getColor(R.color.black_50),
            resources.getColor(R.color.black_25),
            resources.getColor(R.color.black_5)
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewSize = measuredWidth.toFloat().coerceAtMost(measuredHeight.toFloat())
        arcWidth = (viewSize * ARC_WIDTH_SCALE).toFloat()
        circleSpace = viewSize * CIRCLE_SPACE_SCALE.toFloat()
        mPaint.strokeWidth = arcWidth
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // out circle
        drawColorArc(canvas, outCircleColorList, arcWidth / 2, outDestDegree)
        // mid
        val midOffset = arcWidth + arcWidth / 2f + circleSpace
        drawColorArc(canvas, midCircleColorList, midOffset, midDestDegree)
        // inner
        val innerCircleOffset = arcWidth * 2 + arcWidth / 2f + circleSpace + circleSpace
        drawColorArc(canvas, innerCircleColorList, innerCircleOffset, innerDestDegree)
    }

    private fun drawColorArc(
        canvas: Canvas?,
        colorList: IntArray,
        offset: Float,
        destDegree: Float
    ) {
        val centerX = viewSize / 2
        val sweepPosition = floatArrayOf(0f, destDegree / 360f)
        if (destDegree > 360f) {
            sweepPosition[1] = 360f / 360
        }
        val sweepGradient = SweepGradient(centerX, centerX, colorList, sweepPosition)
        //旋转渐变
        if (destDegree > 360f) {
            rotateMatrix.setRotate(START_ANGLE + (destDegree - 360), centerX, centerX)
        } else {
            rotateMatrix.setRotate(START_ANGLE, centerX, centerX)
        }
        sweepGradient.setLocalMatrix(rotateMatrix)
        mPaint.shader = sweepGradient

        circleRect.set(offset, offset, viewSize - offset, viewSize - offset)

        // draw arc
        if (destDegree <= 360) {
            // start circle 超过一周后不绘制开始的圆
            canvas?.drawArc(circleRect, START_ANGLE, destDegree, false, mPaint)
            drawStartEndCircle(canvas, circleRect, centerX - offset, 0f, colorList[0])
        } else {
            canvas?.drawArc(circleRect, START_ANGLE + (destDegree - 360), 360f, false, mPaint)
        }

        // end circle
        drawStartEndCircle(canvas, circleRect, centerX - offset, destDegree, colorList[1], true)
    }

    private fun drawStartEndCircle(
        canvas: Canvas?,
        rect: RectF,
        radius: Float,
        degree: Float,
        color: Int,
        showShadow: Boolean = false
    ) {
        if (showShadow) {
            val shadowPi = (Math.PI / 180f * (degree - 90f + SHADOW_DEGREE_OFFSET))
            // shadow
            val shadowCx = (rect.left + rect.width() / 2 + radius * cos(shadowPi)).toFloat()
            val shadowCy = (rect.top + rect.height() / 2f + radius * sin(shadowPi)).toFloat()
            val radialGradient = RadialGradient(
                shadowCx,
                shadowCy,
                arcWidth / 2,
                shadowColorArray,
                null,
                Shader.TileMode.CLAMP
            )
            shadowPaint.shader = radialGradient
            canvas?.drawCircle(shadowCx, shadowCy, arcWidth / 2, shadowPaint)
        }

        val destPi = (Math.PI / 180f * (degree - 90f)) //换成弧度
        startEndCirclePaint.color = color
        canvas?.drawCircle(
            ((rect.left + rect.width() / 2 + radius * cos(destPi)).toFloat()),  //圆心x
            (rect.top + rect.height() / 2f + radius * sin(destPi)).toFloat(),  //圆心y
            arcWidth / 2,
            startEndCirclePaint
        )
    }

    private val animator: ValueAnimator = ValueAnimator.ofFloat(0f, 1f)

    fun startAnimateProgress() {
        if (animate) {
            if (animator.isRunning) {
                animator.end()
            }

            val originOutDestDegree = outDestDegree
            val originMidDestDegree = midDestDegree
            val originInnerDestDegree = innerDestDegree
            animator.duration = animateDuration.toLong()
            animator.interpolator = AccelerateDecelerateInterpolator()
            animator.addUpdateListener { animation: ValueAnimator ->
                run {
                    val animatedValue = animation.animatedValue as Float
                    outDestDegree = animatedValue * originOutDestDegree
                    midDestDegree = animatedValue * originMidDestDegree
                    innerDestDegree = animatedValue * originInnerDestDegree
                    invalidate()
                }
            }
            animator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    ColorfulProgressCircle.run {
                        outDestDegree = originOutDestDegree
                        midDestDegree = originMidDestDegree
                        innerDestDegree = originInnerDestDegree
                        invalidate()
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
            animator.start()
        } else {
            Timber.tag(TAG).w("animate close")
        }
    }

    fun increaseWithAnim(outDegree: Float, midDegree: Float, innerDegree: Float) {
        if (animate) {
            if (animator.isRunning) {
                animator.end()
            }

            val originOutDestDegree = outDestDegree
            val originMidDestDegree = midDestDegree
            val originInnerDestDegree = innerDestDegree

            animator.duration = animateDuration.toLong()
            animator.interpolator = AccelerateDecelerateInterpolator()
            animator.addUpdateListener { animation: ValueAnimator ->
                run {
                    val animatedValue = animation.animatedValue as Float
                    outDestDegree = originOutDestDegree + animatedValue * outDegree
                    midDestDegree = originMidDestDegree + animatedValue * midDegree
                    innerDestDegree = originInnerDestDegree + animatedValue * innerDegree
                    invalidate()
                }
            }
            animator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    ColorfulProgressCircle.run {
                        outDestDegree += outDegree
                        midDestDegree += midDegree
                        innerDestDegree += innerDegree
                        invalidate()
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
            animator.start()
        } else {
            Timber.tag(TAG).w("animate close")
        }
    }

}