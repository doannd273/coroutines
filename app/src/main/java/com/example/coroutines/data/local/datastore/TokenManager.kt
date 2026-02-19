package com.example.coroutines.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 1. AuthInterceptor → add access token vào header
 * 2. TokenAuthenticator → xử lý 401 và refresh token
 * 3. TokenManager → lưu token
 */
@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.datastore by preferencesDataStore("auth")

    @Volatile
    private var cachedToken: String? = null

    @Volatile
    private var cachedRefreshToken: String? = null

    suspend fun init() {
        val prefs = context.datastore.data.first()
        cachedToken = prefs[TOKEN_KEY]
        cachedRefreshToken = prefs[REFRESH_TOKEN_KEY]
    }

    suspend fun saveToken(token: String) {
        context.datastore.edit {
            it[TOKEN_KEY] = token
        }
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        context.datastore.edit {
            it[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    fun getToken(): String? = cachedToken

    fun getRefreshToken(): String? = cachedRefreshToken

    suspend fun clear() {
        cachedToken = null
        cachedRefreshToken = null
        context.datastore.edit { it.clear() }
    }

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }
}