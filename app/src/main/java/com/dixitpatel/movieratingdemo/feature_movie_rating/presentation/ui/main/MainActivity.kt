package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.main

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dixitpatel.movieratingdemo.R
import com.dixitpatel.movieratingdemo.databinding.ActivityMainBinding
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main)  {

    private lateinit var navController: NavController
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        navController =
            (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController

        binding.bottomNavBar.setupWithNavController(navController)

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPress()
            }
        })
    }

    fun onBackPress() {
        if (backPressedTime + 2500 > System.currentTimeMillis()) {
            finish()
        } else {
            showSnackBar(
                message = getString(R.string.double_press_exit),
                anchor = true)
        }
        backPressedTime = System.currentTimeMillis()
    }
}