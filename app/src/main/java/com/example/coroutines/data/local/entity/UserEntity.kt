package com.example.coroutines.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "users"
)
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val name: String
)