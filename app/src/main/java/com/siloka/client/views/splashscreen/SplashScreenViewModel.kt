package com.siloka.client.views.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.siloka.client.data.models.UserModel
import com.siloka.client.data.preferences.UserPreferences

class SplashScreenViewModel(private val pref: UserPreferences) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
}
