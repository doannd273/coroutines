package com.example.coroutines.domain.repository

import com.example.coroutines.data.local.datastore.TokenManager
import com.example.coroutines.data.remote.api.AuthService
import com.example.coroutines.data.remote.dto.LoginDto
import com.example.coroutines.data.remote.request.LoginRequest
import com.example.coroutines.data.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val tokenManager: TokenManager
) : LoginRepository {
    override suspend fun login(email: String, password: String): LoginDto {
        val response = authService.login(
            LoginRequest(email, password)
        )
        response.token?.let { token ->
            if (token.isNotEmpty()) {
                tokenManager.saveTokens(token, "")
            }
        }
        return response
    }
}