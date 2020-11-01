package io.lab27.githubuser.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable by lazy { CompositeDisposable() }

    protected val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    protected val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    protected val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    abstract fun clearDisposable()

    companion object{
        const val TOAST_DURATION = 1000L
    }
}