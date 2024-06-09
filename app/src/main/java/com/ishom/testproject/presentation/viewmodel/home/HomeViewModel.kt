package com.ishom.testproject.presentation.viewmodel.home

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
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
): ViewModel() {

    private val _userLiveData = MutableLiveData<List<UserEntity>>(listOf())
    val userLiveData: LiveData<List<UserEntity>>
        get() = _userLiveData


    private var _name = ""

    fun fetch(isForced: Boolean) {
        viewModelScope.launch {
            try {
                _userLiveData.value = userRepository.search(_name, isForced = isForced)
            } catch (e: Exception) {
                _userLiveData.value = listOf()
            }
        }
    }

    fun search(name: String) {
        _name = name
        fetch(false)
    }
}