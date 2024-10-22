package com.example.final_assessment.views.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.final_assessment.databinding.ActivityLoginBinding
import com.example.final_assessment.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import com.example.final_assessment.constants.AppConstants
import com.example.final_assessment.views.dashboard.DashboardActivity


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Handle login button click
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.login(username, password)
            } else {
                Toast.makeText(this, "Please enter valid credentials", Toast.LENGTH_SHORT).show()
            }
        }

        loginViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.btnLogin.text = ""
                binding.loadingBar.visibility = View.VISIBLE
                binding.btnLogin.isEnabled = false
            } else {
                binding.btnLogin.text = "Login"
                binding.loadingBar.visibility = View.GONE
                binding.btnLogin.isEnabled = true
            }
        }


        // Observe login result
        loginViewModel.loginResult.observe(this) { result ->
            result.onSuccess { loginResponse ->
                // Navigate to Dashboard, passing the keypass
                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra(AppConstants.KEYPASS, loginResponse.keypass)
                startActivity(intent)
                finish()
            }.onFailure { error ->
                Toast.makeText(this, "Login Failed: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
