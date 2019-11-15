package com.diayan.kaal.ui.boarding

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class OnBoardingAdapter : PagerAdapter() {

    private val items = mutableListOf<OnBoardingPageView>()

    override fun getCount(): Int = items.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = items[position]
        container.addView(item)
        return item
    }


    fun setData(pages: List<OnBoardingPageView>) {
        this.items.clear()
        this.items.addAll(pages)
        notifyDataSetChanged()
    }
}