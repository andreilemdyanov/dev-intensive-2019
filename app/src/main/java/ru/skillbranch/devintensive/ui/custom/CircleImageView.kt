package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import ru.skillbranch.devintensive.R
import kotlin.math.roundToInt


class CircleImageView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {
    companion object {
        private const val DEFAULT_BORDER_WIDTH = 2
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
    }

    private var cv_borderWidth = DEFAULT_BORDER_WIDTH
    private var cv_borderColor = DEFAULT_BORDER_COLOR
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        if(attrs!= null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            cv_borderWidth = a.getInt(R.styleable.CircleImageView_cv_borderWidth, DEFAULT_BORDER_WIDTH)
            cv_borderColor = a.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_BORDER_COLOR)
            a.recycle()
        }
    }


//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        val newHeight = (measuredWidth/aspectRatio).toInt()
//        setMeasuredDimension(measuredWidth, newHeight)
//    }

    override fun onDraw(canvas: Canvas) {
        drawRoundImage(canvas)
        drawStroke(canvas)
    }


    private fun drawStroke(canvas: Canvas) {
//        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val radius = width / 2f

        /* Border paint */
        paint.color = cv_borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = cv_borderWidth.toFloat()
        canvas.drawCircle(width / 2f, width / 2f, radius - cv_borderWidth / 2f, paint)
    }

    private fun drawRoundImage(canvas: Canvas) {
        var b: Bitmap = (drawable as BitmapDrawable).bitmap
        val bitmap = b.copy(Bitmap.Config.ARGB_8888, true)

//         Scale the bitmap
        val scaledBitmap: Bitmap
        val ratio: Float = bitmap.width.toFloat() / bitmap.height.toFloat()
        val height: Int = (width / ratio).roundToInt()
        scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)

//         Cutting the outer of the circle
        val shader: Shader
        shader = BitmapShader(scaledBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        val rect = RectF()
        rect.set(0f, 0f, width.toFloat(), height.toFloat())

        val imagePaint = Paint()
        imagePaint.isAntiAlias = true
        imagePaint.shader = shader
        canvas.drawRoundRect(rect, width.toFloat(), height.toFloat(), imagePaint)

//        val x = width / 2f
//        val y = height / 2f
//        val radius = y
//        canvas.drawCircle(x,y, radius, paint)
    }

    @Dimension fun getBorderWidth(): Int = cv_borderWidth

    fun setBorderWidth(@Dimension dp: Int) {
        cv_borderWidth = dp
    }

    fun getBorderColor(): Int = cv_borderColor

    fun setBorderColor(hex: String) {
        cv_borderColor = Color.parseColor(hex)
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        cv_borderColor = colorId
    }


}