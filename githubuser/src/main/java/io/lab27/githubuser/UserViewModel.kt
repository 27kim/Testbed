package io.lab27.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.lab27.githubuser.datasource.UserRepositoryImpl
import io.lab27.githubuser.datasource.remote.RemoteDataSourceImpl
import io.lab27.githubuser.network.UserResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserViewModel : ViewModel() {
    private val userRepository = UserRepositoryImpl(RemoteDataSourceImpl(), "")

    private var _userList = MutableLiveData<UserResponse>()
    val userList: LiveData<UserResponse>
        get() = _userList

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun fetchUserList(name: String) {
        userRepository.fetchUserList(name)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _isLoading.value = true }
            .doOnError { _isLoading.value = false }
            .doOnSuccess { _isLoading.value = false }
            .subscribe(
                { result -> _userList.value = result },
                { e -> Log.e("ERROR", "${e.message}") }
            )

    }
}