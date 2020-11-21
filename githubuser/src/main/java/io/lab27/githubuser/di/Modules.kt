package io.lab27.githubuser.di

import androidx.room.Room
import io.lab27.githubuser.data.*
import io.lab27.githubuser.data.datasource.UserDataBase
import io.lab27.githubuser.data.datasource.local.LocalDataSource
import io.lab27.githubuser.data.datasource.local.LocalDataSourceImpl
import io.lab27.githubuser.data.datasource.remote.*
import io.lab27.githubuser.network.*
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

val apiModule = module {
    single(named("userApi")) { getUserSource().create(UserApi::class.java) }
    single(named("newsApi")) { getNewsSource().create(NewsApi::class.java) }
    single(named("authApi")) { getAuthSource().create(AuthApi::class.java) }
    single(named("mhApi")) { getUnsafeSource().create(MHApi::class.java) }
}