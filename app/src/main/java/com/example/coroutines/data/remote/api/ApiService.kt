package com.example.coroutines.data.remote.api

import com.example.coroutines.data.remote.dto.UserDto
import com.example.coroutines.data.remote.dto.UserListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/users")
    suspend fun getUsers(@Query("page") page: Int = 1): UserListDto
}