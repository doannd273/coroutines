package com.example.coroutines.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }

    // In-memory cache (Interceptor dùng)
    @Volatile
    private var accessTokenCache: String? = null

    @Volatile
    private var refreshTokenCache: String? = null

    private val mutex = Mutex()

    /**
     * Load token khi app start
     * Gọi 1 lần trong Application
     */
    suspend fun init() {
        val prefs = context.dataStore.data.first()
        accessTokenCache = prefs[ACCESS_TOKEN_KEY]
        refreshTokenCache = prefs[REFRESH_TOKEN_KEY]
    }

    /**
     * Save cả access + refresh
     */
    suspend fun saveTokens(
        accessToken: String,
        refreshToken: String?
    ) = mutex.withLock {

        accessTokenCache = accessToken
        refreshTokenCache = refreshToken

        context.dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = accessToken
            refreshToken?.let {
                prefs[REFRESH_TOKEN_KEY] = it
            }
        }
    }

    /**
     * Chỉ update access token (refresh flow)
     */
    suspend fun updateAccessToken(newToken: String) = mutex.withLock {
        accessTokenCache = newToken
        context.dataStore.edit {
            it[ACCESS_TOKEN_KEY] = newToken
        }
    }

    fun getAccessToken(): String? = accessTokenCache

    fun getRefreshToken(): String? = refreshTokenCache

    suspend fun clear() = mutex.withLock {
        accessTokenCache = null
        refreshTokenCache = null
        context.dataStore.edit { it.clear() }
    }
}