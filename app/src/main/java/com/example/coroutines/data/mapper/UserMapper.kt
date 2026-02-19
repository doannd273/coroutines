package com.example.coroutines.data.mapper

import com.example.coroutines.data.local.entity.UserEntity
import com.example.coroutines.data.remote.dto.UserDto
import com.example.coroutines.domain.model.UserModel

fun UserDto.toEntity() = UserEntity(id, name)

fun List<UserDto>.toEntity(): List<UserEntity> {
    return map { it.toEntity() }
}

fun UserEntity.toModel(): UserModel {
    return UserModel(
        id = id,
        name = name
    )
}

fun List<UserEntity>.toModel(): List<UserModel> {
    return map { it.toModel() }
}