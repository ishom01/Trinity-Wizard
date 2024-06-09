package com.ishom.testproject.presentation.viewmodel.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ishom.testproject.domain.entity.UserEntity
import com.ishom.testproject.domain.repository.UserRepository
import com.ishom.testproject.presentation.viewmodel.SubmitState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository,
): ViewModel() {

    private val _submit = MutableLiveData<SubmitState?>()
    val submit: LiveData<SubmitState?>
        get() = _submit

    fun login(name: String, email: String) {
        viewModelScope.launch {
            try {
                if (name.isEmpty()) {
                    _submit.value = SubmitState(true, Exception("Username is required"))
                    return@launch
                }
                if (email.isEmpty()) {
                    _submit.value = SubmitState(true, Exception("Password is required"))
                    return@launch
                }
                val isFound = repository.login(name, email)
                if (!isFound) {
                    _submit.value = SubmitState(false, Exception("Account not available on list json, Please use this for testing username : test, email password: test@neomail.com"))
                    return@launch
                }
                _submit.value = SubmitState(true, null)
            } catch (e: Exception) {
                _submit.value = SubmitState(false, e)
            }
        }
    }
}