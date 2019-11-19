package com.diayan.kaal.ui.boarding

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.diayan.kaal.MainActivity
import com.diayan.kaal.R
import com.diayan.kaal.util.IntentUtil
import kotlinx.android.synthetic.main.onboarding_view.view.*

class OnBoardingView : FrameLayout, ViewPager.OnPageChangeListener {

    private val numberOfPages by lazy { OnBoardingPage.values().size }

    constructor(context: Context?) : super(context!!) {
        initializeUi(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        initializeUi(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
        initializeUi(context)
    }

    private fun initializeUi(context: Context?) {
        LayoutInflater.from(context).inflate(R.layout.onboarding_view, this, true)

        setupListeners()
    }

    private fun setupListeners() {
        boarding_viewpager.addOnPageChangeListener(this)
        page_indicator.setViewPager(boarding_viewpager)

        previous_button.setOnClickListener {
            boarding_viewpager.setCurrentItem(
                boarding_viewpager.currentItem - 1,
                true
            )
        }
        next_button.setOnClickListener {
            boarding_viewpager.setCurrentItem(
                boarding_viewpager.currentItem + 1,
                true
            )
        }
        finish_button.setOnClickListener {
            Toast.makeText(context, R.string.finish, Toast.LENGTH_SHORT).show()
            IntentUtil.start(context, MainActivity::class.java)
        }
    }

    fun setAdapter(adapter: PagerAdapter) {
        boarding_viewpager.adapter = adapter
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (numberOfPages > 1) {
            val newProgress = (position + positionOffset) / (numberOfPages - 1)
            onBoardingRoot.progress = newProgress
        }
    }

    override fun onPageScrollStateChanged(state: Int) = Unit
    override fun onPageSelected(position: Int) = Unit
}