package io.lab27.githubuser.di

import androidx.room.Room
import io.lab27.githubuser.data.*
import io.lab27.githubuser.viewmodel.UserViewModel
import io.lab27.githubuser.data.datasource.UserDataBase
import io.lab27.githubuser.data.datasource.local.LocalDataSource
import io.lab27.githubuser.data.datasource.local.LocalDataSourceImpl
import io.lab27.githubuser.data.datasource.remote.RemoteDataSource
import io.lab27.githubuser.data.datasource.remote.RemoteDataSourceImpl
import io.lab27.githubuser.viewmodel.AuthViewModel
import io.lab27.githubuser.viewmodel.MHViewModel
import io.lab27.githubuser.viewmodel.NewsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

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
    single<RemoteDataSource> { RemoteDataSourceImpl() }
    single<UserDataBase> {
        Room.databaseBuilder(
            androidContext(), UserDataBase::class.java, "db"
        ).fallbackToDestructiveMigration().build()
    }
}