package com.example.coroutines.data.remote.interceptor

import com.example.coroutines.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("x-api-key", BuildConfig.API_KEY)
            .build()
        return chain.proceed(request)
    }
}