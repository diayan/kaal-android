package com.diayan.kaal.ui.boarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.diayan.kaal.R

enum class OnBoardingPage(
    @StringRes val titleResource: Int,
    @StringRes val descriptionResource: Int,
    @DrawableRes val logoResource: Int
) {

    WELCOME(
        R.string.first_title,
        R.string.first_description,
        R.drawable.ic_welcome
    ),
    EXPLORE(R.string.second_title, R.string.second_description, R.drawable.ic_mole_national),
    EVENTS(R.string.third_title, R.string.third_description, R.drawable.ic_events_onboarding),
    LOCALSTORES(R.string.fourth_title, R.string.fourth_description, R.drawable.ic_stores_onboarding),
}