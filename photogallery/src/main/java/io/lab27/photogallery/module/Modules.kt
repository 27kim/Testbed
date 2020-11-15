package io.lab27.photogallery.module

import io.lab27.photogallery.PhotoViewModel
import org.koin.android.viewmodel.compat.ScopeCompat.viewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // MyViewModel ViewModel
    viewModel { PhotoViewModel(get()) }
}