package com.ishom.testproject.presentation.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.ishom.testproject.R
import com.ishom.testproject.databinding.ActivityLoginBinding
import com.ishom.testproject.presentation.ui.main.MainActivity
import com.ishom.testproject.presentation.viewmodel.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLogin.setOnClickListener {
            viewModel.login(binding.etName.text.toString(), binding.etEmail.text.toString())
        }

        viewModel.submit.observe(this) { state ->
            state?.exception?.let { error ->
                binding.tvError.let {
                    it.isVisible = true
                    it.text = error.message
                }
                return@observe
            }

            if (state?.isSuccess == true) {
                startActivity(Intent(this, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                })
            }
        }
    }
}