package com.siloka.client.views.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.siloka.client.R
import com.siloka.client.databinding.ActivitySplashScreenBinding
import com.siloka.client.views.onboarding.OnboardingActivity
import com.siloka.client.views.settings.SettingsActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setHeader()
        animateLogo()
        goToNextPage()
    }

    private fun setHeader() {
        supportActionBar?.hide()
    }

    private fun animateLogo() {
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.apply {
            ivTvlkLogo.startAnimation(fadeIn)
            tvSiloka.startAnimation(fadeIn)
        }
    }

    private fun goToNextPage() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}