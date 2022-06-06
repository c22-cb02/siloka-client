package com.siloka.client.views.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.siloka.client.data.models.UserModel
import com.siloka.client.data.preferences.UserPreferences
import kotlinx.coroutines.launch

class SettingsViewModel(private val pref: UserPreferences) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    suspend fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
}
