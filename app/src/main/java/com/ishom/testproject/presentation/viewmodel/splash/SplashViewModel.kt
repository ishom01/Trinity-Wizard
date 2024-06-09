package com.ishom.testproject.presentation.viewmodel.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ishom.testproject.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _isLogin = MutableLiveData<Boolean>(null)
    val isLogin: LiveData<Boolean?> get() = _isLogin

    fun checkRoute() {
        viewModelScope.launch {
            delay(2_000)
            _isLogin.value = userRepository.isLogin()
        }
    }
}