package com.diayan.kaal.ui.authentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.diayan.kaal.R
import com.diayan.kaal.helper.SharedPrefManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class AuthenticationActivity : AppCompatActivity(), HasAndroidInjector {

    @set:Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    private val sharedPreferences: SharedPrefManager by lazy {
        sharedPreferences
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        //TODO: Remember to use databinding in here
        //val binding = DataBindingUtil.setContentView<ActivityAuthenticationBinding>(this, R.layout.activity_authentication)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val navController = this.findNavController(R.id.auth_nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        appBarConfiguration = AppBarConfiguration(navController.graph)
            //AppBarConfiguration(navController.graph, )

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.auth_nav_host_fragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)

    }

    private fun createLoginSessionAndStartActivity() {
        sharedPreferences.createLogInSession()
    }
}