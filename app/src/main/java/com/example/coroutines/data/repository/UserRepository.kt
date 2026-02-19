package com.example.coroutines.data.repository

import com.example.coroutines.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun refreshUsers() // remote
    fun observerUser(): Flow<List<UserEntity>> // local
}