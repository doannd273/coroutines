package com.example.coroutines.data.remote.authenticator

import com.example.coroutines.data.local.datastore.TokenManager
import com.example.coroutines.data.remote.api.AuthService
import com.example.coroutines.data.remote.request.RefreshRequest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val authService: AuthService
) : Authenticator {

    private val mutex = Mutex() // khi quá nhiều request bị 401, sẽ chỉ call refreshToken 1 lần

    // trả về khi api 401
    override fun authenticate(route: Route?, response: Response): Request? {
        // nếu retry quá 2 lần sẽ bỏ qua
        if (responseCount(response = response) > 2) return null

        val refreshToken = tokenManager.getRefreshToken() ?: return null

        return runBlocking {
            mutex.withLock {
                val newToken = try {
                    authService.refreshToken(
                        RefreshRequest(refreshToken)
                    )
                } catch (e: Exception) {
                    return@withLock null
                }
                tokenManager.updateAccessToken(newToken.accessToken)

                // call lại api bị 401
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${newToken.accessToken}")
                    .build()
            }
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}