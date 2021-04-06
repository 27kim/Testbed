package io.lab27.githubuser.di

import androidx.room.Room
import io.lab27.githubuser.datasource.UserDataBase
import io.lab27.githubuser.datasource.local.LocalDataSource
import io.lab27.githubuser.datasource.local.LocalDataSourceImpl
import io.lab27.githubuser.datasource.local.PreferenceRepository
import io.lab27.githubuser.datasource.local.PreferenceRepositoryImpl
import io.lab27.githubuser.datasource.remote.*
import io.lab27.githubuser.network.*
import io.lab27.githubuser.network.api.AuthApi
import io.lab27.githubuser.network.api.MHApi
import io.lab27.githubuser.network.api.NewsApi
import io.lab27.githubuser.network.api.UserApi
import io.lab27.githubuser.ui.date.DateFilterViewModel
import io.lab27.githubuser.ui.more.MoreViewModel
import io.lab27.githubuser.viewmodel.AuthViewModel
import io.lab27.githubuser.viewmodel.MHViewModel
import io.lab27.githubuser.viewmodel.NewsViewModel
import io.lab27.githubuser.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { UserViewModel(get()) }
    viewModel { NewsViewModel(get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { MHViewModel(get()) }
    viewModel { MoreViewModel(get()) }
    viewModel { DateFilterViewModel(get()) }
}

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get(named("userApi")), get()) }
    single<NewsRepository> { NewsRepositoryImpl(get(named("newsApi")), get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(named("authApi")), get()) }
    single<EventRepository> { EventRepositoryImpl(get(named("mhApi")), get()) }
    single<MoreRepository> { MoreRepositoryImpl(get(named("moreApi"))) }
    single<PreferenceRepository> { PreferenceRepositoryImpl() }
}

val dataSourceModule = module {
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
    single(named("moreApi")) { getUnsafeSource().create(MHApi::class.java) }
}