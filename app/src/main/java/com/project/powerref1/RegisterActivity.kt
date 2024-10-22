package com.project.powerref1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.powerref1.DataClasses.SignupRequest
import com.project.powerref1.DataClasses.SignupResponse
import com.project.powerref1.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginBtnRegister.setOnClickListener {
            val intent = Intent(this@RegisterActivity,  LoginActivity::class.java)
            startActivity(intent)
        }

        binding.registerBtn.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val confirmPassword = binding.confirmPassword.text.toString().trim()

            // Validate password and confirm password
            if (!isValidPassword(password, confirmPassword)) {
                return@setOnClickListener
            }

            // Prepare the signup request
            val signupRequest = SignupRequest(email, password)

            // Call the signup API
            callSignupApi(signupRequest)
        }

    }

    private fun isValidPassword(password: String, confirmPassword: String): Boolean {
        // Check if password meets length criteria
        if (password.length < 8) {
            Toast.makeText(this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show()
            return false
        }

        // Check if password and confirmPassword match
        if (password != confirmPassword) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


    private fun callSignupApi(signupRequest: SignupRequest) {
        RetrofitClient.instance.signup(signupRequest)
            .enqueue(object : Callback<SignupResponse> {
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {
                    if (response.isSuccessful) {
                        val token = response.body()?.token
                        Toast.makeText(this@RegisterActivity, "Signup successful !", Toast.LENGTH_LONG).show()
                        // Save token for future use (e.g., in SharedPreferences)
                    } else {
                        Toast.makeText(this@RegisterActivity, "Signup failed: ${response.message()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "API call failed: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
    }

}