package com.example.coroutines.ui.user

import androidx.lifecycle.viewModelScope
import com.example.coroutines.data.mapper.toModel
import com.example.coroutines.data.repository.UserRepository
import com.example.coroutines.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    val users = userRepository.observerUser()
        .map {
            it.toModel()
        }.stateIn( // biến thành stateflow
            viewModelScope, // sống theo viewmodel scope
            SharingStarted.WhileSubscribed(5000), // chỉ collect khi có ít nhất 1 subcriber, không thì delay 5s rồi stop
            emptyList()
        )

    fun loadUsers() {
        launchSafely(showLoading = true) {
            userRepository.refreshUsers()
        }
    }
}