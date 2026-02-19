package com.example.coroutines.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutines.data.mapper.toModel
import com.example.coroutines.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val users = userRepository.observerUser()
        .map {
            it.toModel()
        }
        .stateIn( // biến thành stateflow
            viewModelScope, // sống theo viewmodel scope
            SharingStarted.WhileSubscribed(5000), // chỉ collect khi có ít nhất 1 subcriber, không thì delay 5s rồi stop
            emptyList()
        )

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error

    fun loadUsers() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                userRepository.refreshUsers()
            } catch (e: Exception) {
                e.printStackTrace()
                _error.emit(e.message ?: "Có lỗi xảy ra")
            } finally {
                _isLoading.value = false
            }
        }
    }
}