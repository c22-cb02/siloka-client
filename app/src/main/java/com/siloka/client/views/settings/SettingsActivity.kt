package com.siloka.client.views.settings

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
import com.siloka.client.databinding.ActivitySettingsBinding
import com.siloka.client.utilities.delay
import com.siloka.client.utilities.showToast
import com.siloka.client.views.ViewModelFactory
import com.siloka.client.views.main.MainActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingsViewModel
    private lateinit var etName: TextInputEditText
    private lateinit var nameData: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setHeader()
        setViewModel()
        setPrefilledFormData()
        bindSaveUserButton()
    }

    private fun setHeader() {
        supportActionBar?.hide()
    }

    private fun setViewModel () {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreferences.getInstance(dataStore))
        )[SettingsViewModel::class.java]
    }

    private fun setPrefilledFormData() {
        etName = binding.etName
        viewModel.getUser().observe(this, {
            nameData = it.name
            etName.setText(nameData)
        })
    }

    private fun bindSaveUserButton() {
        binding.btnSaveUserData.setOnClickListener {
            val nameInput = etName.text.toString()
            val isFormValid: Boolean = validateData(etName, nameInput)

            if (isFormValid) {
                viewModel.saveUser(UserModel(nameInput))
                showToast(applicationContext, getString(R.string.onboarding_success_toast))
                goToMain()
            }
        }
    }

    private fun validateData(etName: TextInputEditText, nameInput: String): Boolean {
        if (nameInput.isEmpty() or nameInput.isBlank()) {
            etName.error = getString(R.string.et_name_error)
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