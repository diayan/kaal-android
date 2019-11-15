package com.diayan.kaal.ui.boarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.diayan.kaal.R

enum class OnBoardingPage(
    @StringRes val titleResource: Int,
    @StringRes val descriptionResource: Int,
    @DrawableRes val logoResource: Int
) {

    ANDROID(
        R.string.first_title,
        R.string.first_description,
        R.drawable.ic_chat_bubble_black_24dp
    ),
    IOS(R.string.second_title, R.string.second_description, R.drawable.ic_dashboard_black_24dp),
    UNITY(R.string.third_title, R.string.third_description, R.drawable.ic_location),
    KOTLIN(R.string.fourth_title, R.string.fourth_description, R.drawable.ic_event_24dp),
    SWIFT(R.string.fifth_title, R.string.fifth_description, R.drawable.ic_launcher_foreground)
}