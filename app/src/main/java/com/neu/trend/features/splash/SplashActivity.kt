package com.neu.trend.features.splash

import android.os.Bundle
import com.neu.trend.databinding.ActivitySplashBinding
import com.neu.trend.extensions.viewBinding
import com.neu.trend.features.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : BaseActivity() {


    // binding
    private val binding by viewBinding(ActivitySplashBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        screenNavigator.toMainScreen()
    }


    override fun onBackPressed() {

    }


}