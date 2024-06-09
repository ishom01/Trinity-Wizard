package com.ishom.testproject.presentation.ui.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ishom.testproject.databinding.AccountFragmentBinding
import com.ishom.testproject.presentation.ui.detail.CreateOrUpdateActivity
import com.ishom.testproject.presentation.ui.splash.SplashActivity
import com.ishom.testproject.presentation.viewmodel.account.AccountViewModel

class AccountFragment: Fragment() {

    private lateinit var binding: AccountFragmentBinding

    private val viewModel: AccountViewModel by activityViewModels()

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.get()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AccountFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.tvLogout.setOnClickListener {
            viewModel.logout()
        }

        viewModel.logoutState.observe(viewLifecycleOwner) { state ->
            state?.let { isLogout ->
                if (isLogout) {
                    startActivity(Intent(context, SplashActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    })
                } else {
                    Toast.makeText(context, "Failed to logout", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.tvUpdate.setOnClickListener {
            resultLauncher.launch(Intent(context, CreateOrUpdateActivity::class.java).apply {
                putExtra("id", viewModel.user.value?.id)
            })
        }

        viewModel.user.observe(viewLifecycleOwner) {
            it?.let { user ->
                binding.tvCode.text = user.code
                binding.tvName.text = user.name
                binding.tvEmail.text = user.email
                binding.tvDob.text = if (user.dob.isNullOrEmpty()) "-" else user.dob
            }
        }

        viewModel.get()
    }
}