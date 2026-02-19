package com.example.coroutines.domain.repository

import com.example.coroutines.data.local.dao.UserDao
import com.example.coroutines.data.local.entity.UserEntity
import com.example.coroutines.data.mapper.toEntity
import com.example.coroutines.data.remote.api.ApiService
import com.example.coroutines.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
): UserRepository {

    override suspend fun refreshUsers(){
        val userListDto = apiService.getUsers()
        val users = userListDto.data
        userDao.clearUsers()
        userDao.insertUsers(users.toEntity())
    }

    override fun observerUser(): Flow<List<UserEntity>> {
        return userDao.getUsers()
    }
}