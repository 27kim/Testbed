package io.lab27.githubuser

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.lab27.githubuser.data.UserRepositoryImpl
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.data.datasource.UserDataBase
import io.lab27.githubuser.data.datasource.local.LocalDataSourceImpl
import io.lab27.githubuser.data.datasource.remote.RemoteDataSourceImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository =
        UserRepositoryImpl(
            RemoteDataSourceImpl(),
            LocalDataSourceImpl(UserDataBase.getInstance(application)!!)
        )

    private var _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>>
        get() = _userList

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun fetchUserList(name: String) {
        val remote = userRepository.fetchUserList(name)
        val local = userRepository.queryUserLists()

        remote
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _isLoading.value = true }
            .doOnError { _isLoading.value = true }
            .doOnSuccess { _isLoading.value = false }
            .subscribe(
                { result -> _userList.value = result.items },
                { e -> Log.e("fetchUserList", "${e.message}") }
            )

    }
}