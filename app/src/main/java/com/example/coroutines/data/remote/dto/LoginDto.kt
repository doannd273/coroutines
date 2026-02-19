package com.example.coroutines.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("expired_in")
    val expiredTime: Long,
    @SerializedName("error")
    val error: String? = null,
)