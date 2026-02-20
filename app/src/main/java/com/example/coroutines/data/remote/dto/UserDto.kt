package com.example.coroutines.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("first_name")
    val name: String,
    @SerializedName("email")
    val email: String
)