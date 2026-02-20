package com.example.coroutines.ui.login

import com.example.coroutines.data.remote.extension.hasToken
import com.example.coroutines.data.repository.LoginRepository
import com.example.coroutines.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
): BaseViewModel() {
    private val _loginSuccess = MutableSharedFlow<Unit>()
    val loginSuccess = _loginSuccess.asSharedFlow()

    fun login(email: String, password: String) {
        launchSafely(showLoading = true) {
            val result = loginRepository.login(email, password)
            if (result.hasToken()) {
                _loginSuccess.emit(Unit)
            } else {
                throw Exception(result.error ?: "Có lỗi xảy ra")
            }
        }
    }
}