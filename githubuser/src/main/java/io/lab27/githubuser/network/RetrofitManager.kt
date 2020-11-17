package io.lab27.githubuser.network

import io.lab27.githubuser.util.L
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


class RetrofitManager {
    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(15, TimeUnit.MINUTES)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

    private val newsClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
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

    private val authClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(15, TimeUnit.MINUTES)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()


    private fun defaultBuilder(baseUrl: String?): Retrofit.Builder {
        L.d("defaultBuilder ($baseUrl) is called.")
        requireNotNull(baseUrl) { "baseUrl is null." }
        require(baseUrl.isNotEmpty()) { "baseUrl is empty." }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
    }

    private fun newsBuilder(baseUrl: String?): Retrofit.Builder {
        L.d("newsBuilder ($baseUrl) is called.")
        requireNotNull(baseUrl) { "baseUrl is null." }
        require(baseUrl.isNotEmpty()) { "baseUrl is empty." }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(newsClient)
    }

    private fun authBuilder(baseUrl: String?): Retrofit.Builder {
        L.d("newsBuilder ($baseUrl) is called.")
        requireNotNull(baseUrl) { "baseUrl is null." }
        require(baseUrl.isNotEmpty()) { "baseUrl is empty." }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(authClient)
    }

    //github user api
    val userApi: UserApi by lazy {
        val baseUrl = "https://api.github.com/"
        defaultBuilder(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }

    val newsApi: NewsApi by lazy {
        val baseUrl = "https://newsapi.org/"
        newsBuilder(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    val authApi: AuthApi by lazy {
        val baseUrl = "http://35.216.37.218:21549/"
        authBuilder(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    val mhApi: MHApi by lazy {
        val baseUrl = "https://test.happ.hyundai.com/"
        authBuilder(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MHApi::class.java)
    }

    companion object {
        var INSTANCE: RetrofitManager? = null

        fun getInstance(): RetrofitManager {
            return INSTANCE
                ?: RetrofitManager().also {
                    INSTANCE = it
                }
        }
    }
}