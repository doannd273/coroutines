package com.example.coroutines.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutines.data.UserRepository
import com.example.coroutines.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UserUiState {
    data class Success(val data: List<User>): UserUiState()
    data class Error(val message: String): UserUiState()
}

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<UserUiState>(UserUiState.Success(emptyList()))
    val userState: StateFlow<UserUiState> = _userState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadUsers() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = userRepository.fetchUser()
                _userState.value = UserUiState.Success(data = result)
            } catch (e: Exception) {
                e.printStackTrace()
                _userState.value = UserUiState.Error(message = e.message ?: "Có lỗi")
            } finally {
                _isLoading.value = false
            }
        }
    }
}