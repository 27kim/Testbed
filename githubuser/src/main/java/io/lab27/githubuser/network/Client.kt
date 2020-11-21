package io.lab27.githubuser.network

import io.lab27.githubuser.network.UnsafeOkHttpClient.getUnsafeOkHttpClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val defaultClient: OkHttpClient =
    OkHttpClient.Builder().apply {
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    }.build()


fun getNewsClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
            val originalHttpUrl = chain.request().url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("apiKey", "f3ee20613c7146af89a6476cb643914f")
                .addQueryParameter("country", "us")
                .addQueryParameter("pageSize", "3")
                .build()
            request.url(url)
            return@addInterceptor chain.proceed(request.build())
        }
        .connectTimeout(15, TimeUnit.MINUTES)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()
}

fun getUserSource(): Retrofit {
    return Retrofit.Builder()
        .client(defaultClient)
        .baseUrl(USER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun getNewsSource(): Retrofit {
    return Retrofit.Builder()
        .client(getNewsClient())
        .baseUrl(NEWS_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun getAuthSource(): Retrofit {
    return Retrofit.Builder()
        .client(defaultClient)
        .baseUrl(AUTH_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun getUnsafeSource(): Retrofit {
    return Retrofit.Builder()
        .client(getUnsafeOkHttpClient())
        .baseUrl(MH_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private const val CONNECT_TIMEOUT = 10L
private const val WRITE_TIMEOUT = 1L
private const val READ_TIMEOUT = 20L

private const val USER_BASE_URL = "https://api.github.com/"
private const val NEWS_BASE_URL = "https://newsapi.org/"
private const val AUTH_BASE_URL = "http://35.216.37.218:21549/"
private const val MH_BASE_URL = "https://test.happ.hyundai.com/"
