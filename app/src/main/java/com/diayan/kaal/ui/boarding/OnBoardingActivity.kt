package com.diayan.kaal.ui.boarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import com.diayan.kaal.R
import com.diayan.kaal.helper.SharedPrefManager
import com.diayan.kaal.ui.authentication.AuthenticationActivity
import com.diayan.kaal.util.IntentUtil
import com.diayan.kaal.util.Utils
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var sharedPreferencesManager: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        sharedPreferencesManager = SharedPrefManager(this)


        on_boarding_View.setTransitionListener(object: TransitionAdapter(){
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                when(currentId) {
                    R.id.indicatorViewThird -> {
                    }
                }
            }
        })

        finishButton.setOnClickListener {
            sharedPreferencesManager.setIsOnBoarded()
            IntentUtil.start(this, AuthenticationActivity::class.java)
            finish()
        }
    }
}
