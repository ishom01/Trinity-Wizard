package com.ishom.testproject.presentation.viewmodel.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ishom.testproject.domain.entity.UserEntity
import com.ishom.testproject.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository: UserRepository,
): ViewModel() {

    private val _user = MutableLiveData<UserEntity?>(null)
    val user: LiveData<UserEntity?>
        get() = _user

    private val _logoutState = MutableLiveData<Boolean?>()
    val logoutState: LiveData<Boolean?>
        get() = _logoutState


    fun get() {
        viewModelScope.launch {
            try {
                _user.value = repository.getProfile()
            } catch (e: Exception) {
                _user.value = null
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                repository.logout()
                _logoutState.value = true
            } catch (e: Exception) {
                _logoutState.value = false
            }
        }
    }
}