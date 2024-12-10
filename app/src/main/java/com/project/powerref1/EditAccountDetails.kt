package com.project.powerref1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.powerref1.databinding.ActivityEditAccountDetailsBinding
import com.project.powerref1.databinding.ActivityUserAccountBinding

class EditAccountDetails : AppCompatActivity() {
    private lateinit var binding: ActivityEditAccountDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditAccountDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivPopOut.setOnClickListener{
            val intent = Intent(this@EditAccountDetails, UserAccount::class.java)
            startActivity(intent)
        }
    }
}