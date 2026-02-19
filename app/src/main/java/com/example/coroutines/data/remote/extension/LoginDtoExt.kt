package com.example.coroutines.data.remote.extension

import com.example.coroutines.data.remote.dto.LoginDto

fun LoginDto.hasToken(): Boolean {
    return !this.token.isNullOrEmpty()
}