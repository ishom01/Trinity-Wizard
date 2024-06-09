package com.ishom.testproject.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ishom.testproject.R
import com.ishom.testproject.databinding.ActivityMainBinding
import com.ishom.testproject.presentation.ui.account.AccountFragment
import com.ishom.testproject.presentation.ui.home.adaper.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener { it ->
            when (it.itemId) {
                R.id.home -> supportFragmentManager.beginTransaction().replace(binding.frame.id, HomeFragment()).commitNow()
                R.id.account -> supportFragmentManager.beginTransaction().replace(binding.frame.id, AccountFragment()).commitNow()
            }
            true
        }
        binding.bottomNavigation.selectedItemId = R.id.home
    }
}