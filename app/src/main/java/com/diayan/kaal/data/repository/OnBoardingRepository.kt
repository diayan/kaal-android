package com.diayan.kaal.data.repository

import com.diayan.kaal.data.dao.OnBoardingDao
import javax.inject.Inject

class OnBoardingRepository @Inject constructor(private val onBoardingDao: OnBoardingDao) {

    fun updateUserPreferences(data: MutableMap<String, Any>) {
        onBoardingDao.updateUserPreference(data)
    }


    suspend fun checkOnBoardingStatus(uid: String) = onBoardingDao.checkIfUserIsOnBoarded(uid)
}