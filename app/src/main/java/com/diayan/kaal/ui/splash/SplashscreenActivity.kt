package com.diayan.kaal.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.diayan.kaal.MainActivity
import com.diayan.kaal.R
import com.diayan.kaal.helper.SharedPrefManager
import com.diayan.kaal.ui.authentication.AuthenticationActivity
import com.diayan.kaal.ui.boarding.OnBoardingActivity
import com.diayan.kaal.util.IntentUtil
import com.diayan.kaal.util.launchActivity

class SplashscreenActivity : AppCompatActivity() {

    private lateinit var sharedPreferencesManager: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        sharedPreferencesManager = SharedPrefManager(this)
        checkIfUserIsOnBoardedOrLoggedIn()
    }


    private fun checkIfUserIsOnBoardedOrLoggedIn() {

        if(sharedPreferencesManager.isLoggedIn() && sharedPreferencesManager.isOnBoarded()) {
            //Log.d("ISLoggedIn", sharedPreferencesManager.isLoggedIn().toString())
            launchActivity<MainActivity> {
                finish()
            }
        }else if(sharedPreferencesManager.isOnBoarded() && !sharedPreferencesManager.isLoggedIn()) {
            launchActivity<AuthenticationActivity> {
                finish()
            }
        }else
            launchActivity<OnBoardingActivity> {  }
        finish()
    }

    fun start(context: Context?, cls: Class<out Any>) {
        val intent = Intent(context, cls)
        context?.startActivity(intent)
        finish()
    }
}