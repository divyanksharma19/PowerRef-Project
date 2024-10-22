package com.project.powerref1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.powerref1.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findViewById<Button>(R.id.attachButton).setOnClickListener{
            startActivity(Intent(this,HomeActivity::class.java))
        }
        binding.registerBtn.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }


    }


}