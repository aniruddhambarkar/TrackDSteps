package com.aniruddhambarkar.trackdsteps

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.aniruddhambarkar.trackdsteps.home.MainActivity
import com.aniruddhambarkar.trackdsteps.login.LoginActivity

class RoutingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { true }
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}