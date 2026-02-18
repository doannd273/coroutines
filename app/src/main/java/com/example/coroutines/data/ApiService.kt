package com.example.coroutines.data

import com.example.coroutines.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("/users")
    suspend fun getUsers(): List<User>
}