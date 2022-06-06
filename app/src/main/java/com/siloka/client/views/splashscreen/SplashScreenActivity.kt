package com.siloka.client.views.splashscreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.siloka.client.R
import com.siloka.client.data.preferences.UserPreferences
import com.siloka.client.databinding.ActivitySplashScreenBinding
import com.siloka.client.views.ViewModelFactory
import com.siloka.client.views.main.MainActivity
import com.siloka.client.views.onboarding.OnboardingActivity
import com.siloka.client.views.settings.SettingsActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

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
        val pref = UserPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(
            this,
            ViewModelFactory(pref)
        )[SplashScreenViewModel::class.java]

        viewModel.getUser().observe(this, {
            if (it.name.isNotBlank()) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 2000)
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, OnboardingActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 3000)
            }
        })
    }
}