package com.diayan.kaal.ui.boarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diayan.kaal.R
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {

    private val adapter = OnBoardingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        val data = OnBoardingPage
            .values()
            .map { onBoardingPageData ->
                val pageView = OnBoardingPageView(this)
                pageView.setPageData(onBoardingPageData)

                pageView
            }

        adapter.setData(data)
        on_boarding_View.setAdapter(adapter)
    }
}
