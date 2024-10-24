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
            val firstname = binding.firstname.text.toString()
            val lastname = binding.lastname.text?.toString()
            val password = binding.password.text.toString().trim()
            val confirmPassword = binding.confirmPassword.text.toString().trim()

            if (!isValidEmail(email)) {
                return@setOnClickListener
            }


            // Validate first name
            if (!isValidFirstName(firstname)) {
                return@setOnClickListener
            }
            // Validate password and confirm password
            if (!isValidPassword(password, confirmPassword)) {
                return@setOnClickListener
            }

            // Prepare the signup request
            val signupRequest = SignupRequest(email, firstname,lastname,password)

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

    private fun isValidFirstName(firstName: String): Boolean {
        return if (firstName.isEmpty()) {
            Toast.makeText(this, "First name cannot be empty", Toast.LENGTH_SHORT).show()
            false
        } else if (!firstName.matches("^[a-zA-Z]+$".toRegex())) {
            Toast.makeText(this, "First name can only contain letters", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }
    private fun isValidEmail(email: String): Boolean {
        return if (email.isEmpty()) {
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show()
            false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
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
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Needed if starting activity from non-activity context
                        startActivity(intent)
                        finish()
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