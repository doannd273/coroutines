package com.example.coroutines.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutines.data.remote.extension.hasToken
import com.example.coroutines.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
): ViewModel() {
    private val _loginSuccess = MutableSharedFlow<Unit>()
    val loginSuccess = _loginSuccess.asSharedFlow()

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val result = loginRepository.login(email, password)

                if (result.hasToken()) {
                    _loginSuccess.emit(Unit)
                } else {
                    _error.emit(result.error ?: "Có lỗi xảy ra")
                }

            } catch (e: Exception) {
                _error.emit(e.message ?: "Có lỗi xảy ra")
            } finally {
                _isLoading.value = false
            }
        }
    }
}