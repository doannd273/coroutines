package com.example.coroutines.data.repository

import com.example.coroutines.data.remote.dto.LoginDto

interface LoginRepository {
    suspend fun login(email: String, password: String): LoginDto
}