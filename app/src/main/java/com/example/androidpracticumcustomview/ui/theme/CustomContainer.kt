package com.example.androidpracticumcustomview.ui.theme

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.core.view.isVisible

/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */

class CustomContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        children.forEach {
            val lp = it.layoutParams as? MarginLayoutParams
            val heightSpec = getChildMeasureSpec(
                heightMeasureSpec,
                (lp?.topMargin ?: 0) + (lp?.bottomMargin
                    ?: 0) + this.paddingTop + this.paddingBottom,
                WRAP_CONTENT
            )
            val widthSpec = getChildMeasureSpec(
                widthMeasureSpec,
                (lp?.leftMargin ?: 0) + (lp?.rightMargin
                    ?: 0) + this.paddingLeft + this.paddingRight,
                WRAP_CONTENT
            )
            it.measure(widthSpec, heightSpec)
        }
        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.isVisible) {
                val width = child.measuredWidth
                val height = child.measuredHeight
                val childTop = ((bottom - top) - height) / 2
                val childLeft = left + (right - left - width) / 2
                child.layout(
                    childLeft,
                    childTop,
                    childLeft + width,
                    childTop + width
                )
                if (child.alpha == 0f) {
                    val translate = ((this.measuredHeight / 2) - child.measuredHeight).toFloat()
                    child.animate()
                        .alpha(1f)
                        .translationY(if (i == 0) -translate else translate)
                        .setDuration(5000L).start()
                }
            }
        }
    }

    override fun addView(child: View) {
        if (this.childCount == 2) throw IllegalStateException()
        child.apply { alpha = 0f }
        this.addView(child, childCount)
    }
}