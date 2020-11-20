package io.lab27.githubuser.di

import androidx.room.Room
import io.lab27.githubuser.data.*
import io.lab27.githubuser.data.datasource.UserDataBase
import io.lab27.githubuser.data.datasource.local.LocalDataSource
import io.lab27.githubuser.data.datasource.local.LocalDataSourceImpl
import io.lab27.githubuser.data.datasource.remote.*
import io.lab27.githubuser.network.UnsafeOkHttpClient
import io.lab27.githubuser.network.UnsafeOkHttpClient.getUnsafeOkHttpClient
import io.lab27.githubuser.network.api.AuthApi
import io.lab27.githubuser.network.api.MHApi
import io.lab27.githubuser.network.api.NewsApi
import io.lab27.githubuser.network.api.UserApi
import io.lab27.githubuser.viewmodel.AuthViewModel
import io.lab27.githubuser.viewmodel.MHViewModel
import io.lab27.githubuser.viewmodel.NewsViewModel
import io.lab27.githubuser.viewmodel.UserViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {
    viewModel { UserViewModel(get()) }
    viewModel { NewsViewModel(get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { MHViewModel(get()) }
}

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<NewsRepository> { NewsRepositoryImpl(get(), get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<EventRepository> { EventRepositoryImpl(get(), get()) }
}

val dataSourceModule = module {
    single<UserDataSource> { UserDataSourceImpl(get(named("userApi"))) }
    single<NewsDataSource> { NewsDataSourceImpl(get(named("newsApi"))) }
    single<AuthDataSource> { AuthDataSourceImpl(get(named("authApi"))) }
    single<EventDataSource> { EventDataSourceImpl(get(named("mhApi"))) }

    single<LocalDataSource> { LocalDataSourceImpl(get()) }
    single<UserDataBase> {
        Room.databaseBuilder(
            androidContext(), UserDataBase::class.java, "db"
        ).fallbackToDestructiveMigration().build()
    }
}

val clientModule = module {
    single<OkHttpClient>(named("defaultClient")) {
        OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    }

    single<OkHttpClient>(named("newsClient")) {
        OkHttpClient.Builder()
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

    single<OkHttpClient>(named("unsafeClient")) {
        getUnsafeOkHttpClient()
    }
}

val retrofitModule = module{
    single(named("userSource")) {
        Retrofit.Builder()
            .client(get(named("defaultClient")))
            .baseUrl(USER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single(named("newsSource")) {
        Retrofit.Builder()
            .client(get(named("newsClient")))
            .baseUrl(NEWS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single(named("authSource")) {
        Retrofit.Builder()
            .client(get(named("defaultClient")))
            .baseUrl(AUTH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single(named("unsafeSource")) {
        Retrofit.Builder()
            .client(get(named("unsafeClient")))
            .baseUrl(MH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

val apiModule = module {
    single(named("userApi")) { get<Retrofit>(named("userSource")).create(UserApi::class.java) }
    single(named("newsApi")) { get<Retrofit>(named("newsSource")).create(NewsApi::class.java) }
    single(named("authApi")) { get<Retrofit>(named("authSource")).create(AuthApi::class.java) }
    single(named("mhApi")) { get<Retrofit>(named("unsafeSource")).create(MHApi::class.java) }
}

private const val CONNECT_TIMEOUT = 10L
private const val WRITE_TIMEOUT = 1L
private const val READ_TIMEOUT = 20L
private const val USER_BASE_URL = "https://api.github.com/"
private const val NEWS_BASE_URL = "https://newsapi.org/"
private const val AUTH_BASE_URL = "http://35.216.37.218:21549/"
private const val MH_BASE_URL = "https://test.happ.hyundai.com/"
