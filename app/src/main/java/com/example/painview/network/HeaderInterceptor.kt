package com.example.painview.network

import com.example.painview.constance.Constance
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder().addHeader("Authorization", "Bearer ${Constance.TOKEN}")
                .build()
        return chain.proceed(request)
    }
}