package com.diayan.kaal.ui.boarding

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.diayan.kaal.R
import kotlinx.android.synthetic.main.item_on_boarding.view.*

class OnBoardingPageView : FrameLayout {

    private lateinit var page: OnBoardingPage

    constructor(context: Context?) : super(context!!)

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    )

    init {
        LayoutInflater.from(context).inflate(R.layout.item_on_boarding, this, true)
    }

    fun setPageData(onBoardingPage: OnBoardingPage) {
        this.page = onBoardingPage

        logo_imageView.setImageResource(onBoardingPage.logoResource)
        title_textView.text = resources.getString(page.titleResource)
        description_textView.text = resources.getString(page.descriptionResource)
    }
}