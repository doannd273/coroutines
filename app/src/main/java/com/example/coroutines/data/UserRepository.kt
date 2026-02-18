package com.example.coroutines.data

import com.example.coroutines.model.User

interface UserRepository {
    suspend fun fetchUser(): List<User>
}