package com.diayan.kaal.helper

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(val context: Context) {

    private var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor
    private val PRIVATE_MODE = 0

    init {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = sharedPreferences.edit()
        editor.apply()
    }

    companion object {
        const val PREF_NAME = "com.diayan.kaal.pref"
        const val IS_LOGGEDIN = "isLoggedIn"
        const val IS_OnBoarded = "hasOnBoarded"
    }

    fun createLogInSession() {
        editor.putBoolean(IS_LOGGEDIN, true)
        editor.apply()
    }

    fun isLoggedIn(): Boolean = sharedPreferences.getBoolean(IS_LOGGEDIN, false)
    
    fun setIsOnboarded() {
        editor.putBoolean(IS_OnBoarded, true)
        editor.apply()
    }

    fun isOnboarded(): Boolean = sharedPreferences.getBoolean(IS_OnBoarded, false)

    fun clearSharedPreferences() {
        editor.clear()
        editor.commit()
    }
}