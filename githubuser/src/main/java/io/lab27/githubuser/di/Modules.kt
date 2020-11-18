package io.lab27.githubuser.di

import androidx.room.Room
import io.lab27.githubuser.data.*
import io.lab27.githubuser.viewmodel.UserViewModel
import io.lab27.githubuser.data.datasource.UserDataBase
import io.lab27.githubuser.data.datasource.local.LocalDataSource
import io.lab27.githubuser.data.datasource.local.LocalDataSourceImpl
import io.lab27.githubuser.data.datasource.remote.RemoteDataSource
import io.lab27.githubuser.data.datasource.remote.RemoteDataSourceImpl
import io.lab27.githubuser.network.api.UserApi
import io.lab27.githubuser.viewmodel.AuthViewModel
import io.lab27.githubuser.viewmodel.MHViewModel
import io.lab27.githubuser.viewmodel.NewsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    }

    single {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl(USER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<UserApi> { get<Retrofit>().create(UserApi::class.java) }
}

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
}




val dataSourceModule = module {
    single<LocalDataSource> { LocalDataSourceImpl(get()) }
    single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    single<UserDataBase> {
        Room.databaseBuilder(
            androidContext(), UserDataBase::class.java, "db"
        ).fallbackToDestructiveMigration().build()
    }
}


private const val CONNECT_TIMEOUT = 10L
private const val WRITE_TIMEOUT = 1L
private const val READ_TIMEOUT = 20L
private const val USER_BASE_URL = "https://api.github.com/"
