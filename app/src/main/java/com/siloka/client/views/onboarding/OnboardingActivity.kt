package com.siloka.client.views.onboarding

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.siloka.client.R
import com.siloka.client.data.models.UserModel
import com.siloka.client.data.preferences.UserPreferences
import com.siloka.client.databinding.ActivityOnboardingBinding
import com.siloka.client.utilities.delay
import com.siloka.client.utilities.showToast
import com.siloka.client.views.ViewModelFactory
import com.siloka.client.views.main.MainActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var viewModel: OnboardingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setHeader()
        setViewModel()
        bindSaveUserButton()
    }

    private fun setHeader() {
        supportActionBar?.hide()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreferences.getInstance(dataStore))
        )[OnboardingViewModel::class.java]
    }

    private fun bindSaveUserButton() {
        binding.btnSaveUserData.setOnClickListener {
            val et = binding.etName
            val name = et.text.toString()
            val isFormValid: Boolean = validateData(et, name)

            if (isFormValid) {
                viewModel.saveUser(UserModel(name))
                showToast(applicationContext, getString(R.string.onboarding_success_toast))
                goToMain()
            }
        }
    }

    private fun validateData(et: TextInputEditText, name: String): Boolean {
        if (name.isEmpty() or name.isBlank()) {
            et.error = getString(R.string.et_name_error)
            return false
        }
        return true
    }

    private fun goToMain() {
        delay({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 500)
    }
}