package com.ishom.testproject.presentation.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.ishom.testproject.R
import com.ishom.testproject.databinding.ActivityCreateOrUpdateBinding
import com.ishom.testproject.domain.entity.UserEntity
import com.ishom.testproject.presentation.viewmodel.createOrUpdate.CreateOrUpdateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateOrUpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateOrUpdateBinding
    private val viewModel: CreateOrUpdateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateOrUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            viewModel.save(
                firstName = binding.etFirstName.text.toString(),
                lastName = binding.etLastName.text.toString(),
                email = binding.etEmail.text.toString(),
                phone = binding.etPhoneNumber.text.toString(),
            )
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        viewModel.user.observe(this) { user ->
            if (user != null) {
                binding.tvCode.let {
                    it.isVisible = true
                    it.text = user.code
                }

                binding.icIcon.isVisible = false
                binding.btnSubmit.text = "Update"
                binding.btnRemove.isVisible = true

                binding.etEmail.setText(user.email)
                binding.etFirstName.setText(user.firstName)
                binding.etLastName.setText(user.lastName)
                binding.etPhoneNumber.setText(user.phone)
            } else {
                binding.icIcon.isVisible = true
                binding.btnRemove.isVisible = false
                binding.btnSubmit.text = "Save"
                binding.tvCode.isVisible = false
            }
        }

        viewModel.submitState.observe(this) { state ->
            state.exception?.let {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                return@observe
            }

            if (state.isSuccess) {
                finish()
            }
        }

        val id = intent.getStringExtra("id")
        viewModel.get(id ?: "")
    }
}