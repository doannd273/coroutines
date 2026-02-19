package com.example.coroutines.data.remote.api

import com.example.coroutines.data.remote.dto.LoginDto
import com.example.coroutines.data.remote.dto.RefreshDto
import com.example.coroutines.data.remote.dto.UserListDto
import com.example.coroutines.data.remote.request.LoginRequest
import com.example.coroutines.data.remote.request.RefreshRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @POST("/api/login")
    suspend fun login(@Body request: LoginRequest): LoginDto
    @POST("/api/refresh")
    suspend fun refreshToken(@Body request: RefreshRequest): RefreshDto
}