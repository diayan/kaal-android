package com.diayan.kaal.helper

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class SquareFrameLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context!!, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (widthSize == 0 && heightSize == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec) // If there are no constraints on size, let FrameLayout measure
            val minSize = Math.min(measuredWidth, measuredHeight) // Now use the smallest of the measured dimensions for both dimensions
            setMeasuredDimension(minSize, minSize)
            return
        }

        val size: Int
        size = if (widthSize == 0 || heightSize == 0) {
            Math.max(widthSize, heightSize) // If one of the dimensions has no restriction on size, set both dimensions to be the on that does
        } else {
            Math.min(widthSize, heightSize) // Both dimensions have restrictions on size, set both dimensions to be the smallest of the two
        }
        val newMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY)
        super.onMeasure(newMeasureSpec, newMeasureSpec)
    }
}
