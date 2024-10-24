package com.project.powerref1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // If you have this method implemented

        // Set the content view for the splash screen
        setContentView(R.layout.activity_splash_screen)

        // Launch a coroutine for the delay and navigation
        GlobalScope.launch(Dispatchers.Main) {
            delay(2000) // Show splash for 2 seconds

            // Check if the user is already logged in
            val sharedPreferences: SharedPreferences = getSharedPreferences("PowerRefPrefs", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("token", null)

            // Decide which activity to navigate to
            val intent = if (token != null) {
                Intent(this@SplashScreen, HomeActivity::class.java) // User is logged in
            } else {
                Intent(this@SplashScreen, LoginActivity::class.java) // User is not logged in
            }

            startActivity(intent)
            finish() // Close the splash screen activity
        }
    }
}