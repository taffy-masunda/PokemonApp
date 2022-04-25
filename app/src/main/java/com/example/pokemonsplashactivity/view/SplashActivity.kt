package com.example.pokemonsplashactivity.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pokemonsplashactivity.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        binding.splashContainer.alpha = 0f
        binding.splashContainer.animate().setDuration(1500).alpha(1f).withEndAction{
            val openMainScreenIntent = Intent(this, MainActivity::class.java)
            startActivity(openMainScreenIntent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
        setContentView(binding.root)
    }
}