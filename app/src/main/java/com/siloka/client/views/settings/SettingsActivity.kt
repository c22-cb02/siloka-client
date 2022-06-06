package com.siloka.client.views.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.siloka.client.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setHeader()
    }

    private fun setHeader() {
        supportActionBar?.hide()
    }
}