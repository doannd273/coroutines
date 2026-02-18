package com.example.coroutines.data

import com.example.coroutines.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): UserRepository {

    override suspend fun fetchUser(): List<User> {
        return apiService.getUsers()
    }
}