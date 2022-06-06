package com.siloka.client.views.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siloka.client.data.models.UserModel
import com.siloka.client.data.preferences.UserPreferences
import kotlinx.coroutines.launch

class OnboardingViewModel(private val pref: UserPreferences) : ViewModel() {
    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
}
