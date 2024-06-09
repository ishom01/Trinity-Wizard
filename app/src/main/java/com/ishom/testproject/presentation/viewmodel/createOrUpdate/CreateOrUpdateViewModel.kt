package com.ishom.testproject.presentation.viewmodel.createOrUpdate

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
class CreateOrUpdateViewModel @Inject constructor(
    private val repository: UserRepository,
): ViewModel() {

    private val _user = MutableLiveData<UserEntity?>(null)
    val user: LiveData<UserEntity?>
        get() = _user

    private val _submitState = MutableLiveData<SubmitState>()
    val submitState: LiveData<SubmitState>
        get() = _submitState


    fun get(id: String) {
        viewModelScope.launch {
            try {
                _user.value = repository.byId(id)
            } catch (e: Exception) {
                Log.e("Debug", "Debug flow $e")
                _user.value = null
            }
        }
    }

    fun save(firstName: String, lastName: String, email: String, phone: String) {
        viewModelScope.launch {
            try {
                if (firstName.isEmpty()) {
                    _submitState.value = SubmitState(exception = Exception("First name is required"))
                    return@launch
                }

                if (lastName.isEmpty()) {
                    _submitState.value = SubmitState(exception = Exception("Last name is required"))
                    return@launch
                }

                if (_user.value != null) {
                    repository.edit(_user.value?.id ?: "", firstName, lastName, email, phone)
                } else {
                    repository.add(firstName, lastName, email, phone)
                }
                _submitState.value = SubmitState(isSuccess = true, exception = null)
            } catch (e: Exception) {
                _submitState.value = SubmitState(isSuccess = false, exception = e)
            }
        }
    }
}