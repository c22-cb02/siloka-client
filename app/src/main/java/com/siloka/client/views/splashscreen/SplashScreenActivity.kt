package com.siloka.client.views.splashscreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.siloka.client.R
import com.siloka.client.data.preferences.UserPreferences
import com.siloka.client.databinding.ActivitySplashScreenBinding
import com.siloka.client.utilities.delay
import com.siloka.client.views.ViewModelFactory
import com.siloka.client.views.main.MainActivity
import com.siloka.client.views.onboarding.OnboardingActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var viewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setHeader()
        setViewModel()
        animateLogo()
        goToNextPage()
    }

    private fun setHeader() {
        supportActionBar?.hide()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreferences.getInstance(dataStore))
        )[SplashScreenViewModel::class.java]
    }

    private fun animateLogo() {
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.apply {
            ivTvlkLogo.startAnimation(fadeIn)
            tvSiloka.startAnimation(fadeIn)
        }
    }

    private fun goToNextPage() {
        viewModel.getUser().observe(this, {
            if (it.name.isNotBlank()) {
                delay({
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 3000)
            } else {
                delay({
                    val intent = Intent(this, OnboardingActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 3000)
            }
        })
    }
}