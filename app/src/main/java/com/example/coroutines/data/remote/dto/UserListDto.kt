package com.example.coroutines.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserListDto(
    @SerializedName("data")
    val data: List<UserDto>
)