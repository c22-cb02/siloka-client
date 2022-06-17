package com.siloka.client.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.siloka.client.data.preferences.UserPreferences
import com.siloka.client.views.main.MainViewModel
import com.siloka.client.views.onboarding.OnboardingViewModel
import com.siloka.client.views.settings.SettingsViewModel
import com.siloka.client.views.splashscreen.SplashScreenViewModel

class ViewModelFactory(
    private val pref: UserPreferences
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashScreenViewModel::class.java) -> {
                SplashScreenViewModel(pref) as T
            }
            modelClass.isAssignableFrom(OnboardingViewModel::class.java) -> {
                OnboardingViewModel(pref) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(pref) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
