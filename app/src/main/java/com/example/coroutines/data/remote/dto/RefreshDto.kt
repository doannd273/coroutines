package com.example.coroutines.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RefreshDto(
    @SerializedName("access_token")
    val accessToken: String
)