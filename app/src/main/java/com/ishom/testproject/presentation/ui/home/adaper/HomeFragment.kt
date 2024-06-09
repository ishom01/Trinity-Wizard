package com.ishom.testproject.presentation.ui.home.adaper

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ishom.testproject.R
import com.ishom.testproject.databinding.HomeFragmentBinding
import com.ishom.testproject.domain.entity.UserEntity
import com.ishom.testproject.presentation.ui.detail.CreateOrUpdateActivity
import com.ishom.testproject.presentation.viewmodel.home.HomeViewModel


class HomeFragment: Fragment() {

    private lateinit var binding: HomeFragmentBinding

    private val viewModel: HomeViewModel by activityViewModels()

    private val adapter = UserAdapter { user ->
        openDetail(user)
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.fetch(false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.swipe.setOnRefreshListener {
            viewModel.fetch(isForced = true)
        }

        binding.edtSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.search(s.toString())
                context?.let {
                    if (s.isEmpty()) {
                        binding.etBackground.setBackgroundResource(R.drawable.ic_round)
                        binding.search.setColorFilter(ContextCompat.getColor(it, R.color.dark_gray), PorterDuff.Mode.SRC_IN)
                    } else {
                        binding.etBackground.setBackgroundResource(R.drawable.ic_round_blue)
                        binding.search.setColorFilter(ContextCompat.getColor(it, R.color.blue), PorterDuff.Mode.SRC_IN)
                    }

                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.search.setOnClickListener {
            viewModel.search(binding.edtSearch.text.toString())
        }

        viewModel.userLiveData.observe(viewLifecycleOwner) {
            binding.swipe.isRefreshing = false
            binding.tvEmpty.isVisible = it.isEmpty( )
            binding.swipe.isVisible = it.isNotEmpty()
            adapter.setUsers(it)
        }

        binding.recyclerview.let {
            it.layoutManager = GridLayoutManager(context, 2)
            it.adapter = adapter
        }

        binding.btnAdd.setOnClickListener {
            openDetail()
        }

        viewModel.fetch(isForced = false)
    }

    private fun openDetail(userEntity: UserEntity? = null) {
        resultLauncher.launch(Intent(context, CreateOrUpdateActivity::class.java).apply {
            putExtra("id", userEntity?.id)
        })
    }

}