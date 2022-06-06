package com.siloka.client.views.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.siloka.client.R
import com.siloka.client.databinding.ActivityOnboardingBinding
import com.siloka.client.views.main.MainActivity

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }
}