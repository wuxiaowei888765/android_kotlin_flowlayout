package view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * Created by wuxiaowei on 2018/1/10.
 */
class FlowLayout(context: Context, attributeSet: AttributeSet?, defStyle: Int) : ViewGroup(context, attributeSet, defStyle) {

    constructor (context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor (context: Context) : this(context, null)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val sizeWidth: Int = MeasureSpec.getSize(widthMeasureSpec)
        val modeWidth: Int = MeasureSpec.getMode(widthMeasureSpec)

        val sizeHeight: Int = MeasureSpec.getSize(heightMeasureSpec)
        val modeHeight: Int = MeasureSpec.getMode(heightMeasureSpec)

        var width = 0
        var height = 0

        var lineHeight = 0
        var lineWidth = 0

        val cCount = childCount

        for (i in 0 until cCount) {

            val child: View = getChildAt(i)

            measureChild(child, widthMeasureSpec, heightMeasureSpec)

            val lp: MarginLayoutParams = child.layoutParams as MarginLayoutParams

            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin

            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin

            if (lineWidth + childWidth > sizeWidth) {

                width = Math.max(width, lineWidth)

                lineWidth = childWidth

                height += lineHeight

                lineHeight = childHeight

            } else {

                lineWidth += childWidth

                lineHeight = Math.max(childHeight, lineHeight)
            }

            if (i == cCount - 1) {
                width = Math.max(width, lineWidth)
                height += lineHeight
            }
        }
        setMeasuredDimension(
                if (modeWidth == MeasureSpec.EXACTLY) sizeWidth else width,
                if (modeHeight == MeasureSpec.EXACTLY) sizeHeight else height)
    }

    var mAllViews = ArrayList<List<View>>()

    var mLineHeight = ArrayList<Int>()

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        mAllViews.clear()
        mLineHeight.clear()

        var width = width

        var lineWidth = 0
        var lineHeight = 0

        var lineViews = ArrayList<View>()

        var cCount = childCount

        for (i in 0 until cCount) {
            val child = getChildAt(i)
            var lp: MarginLayoutParams = child.layoutParams as MarginLayoutParams

            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            if (lineWidth + childWidth + lp.leftMargin + lp.rightMargin > width) {
                mLineHeight.add(lineHeight)

                mAllViews.add(lineViews)

                lineWidth = 0

                lineHeight = childHeight + lp.topMargin + lp.bottomMargin

                lineViews = ArrayList()
            }

            lineWidth += childWidth + lp.leftMargin + lp.rightMargin
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin)

            lineViews.add(child)
        }
        mLineHeight.add(lineHeight)
        mAllViews.add(lineViews)

        var left = 0
        var top = 0

        val lineNun = mAllViews.size

        for (i in 0 until lineNun) {

            lineViews = mAllViews[i] as ArrayList<View>
            lineHeight = mLineHeight[i]

            for (j in 0 until lineViews.size) {

                val child = lineViews[j]

                if (child.visibility == View.GONE) {
                    continue
                }

                val lp = child.layoutParams as MarginLayoutParams
                val lc = left + lp.leftMargin
                val tc = top + lp.topMargin
                val rc = lc + child.measuredWidth
                val bc = tc + child.measuredHeight
                child.layout(lc, tc, rc, bc)

                left += child.measuredWidth + lp.leftMargin + lp.rightMargin
            }

            left = 0
            top += lineHeight
        }

    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {

        return MarginLayoutParams(context, attrs)
    }

}