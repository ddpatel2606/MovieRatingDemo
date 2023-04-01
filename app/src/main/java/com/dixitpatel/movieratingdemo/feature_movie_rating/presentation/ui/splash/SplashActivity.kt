package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.coroutineScope
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val installSplashScreen = installSplashScreen()
            installSplashScreen.setKeepOnScreenCondition{ false }
        }
        super.onCreate(savedInstanceState)
        lifecycle.coroutineScope.launch {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}