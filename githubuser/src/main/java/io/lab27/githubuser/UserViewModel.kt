package io.lab27.githubuser

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
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

    private var _localUserList = MutableLiveData<List<User>>()
    val localUserList: LiveData<List<User>>
        get() = _localUserList

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    val combinedResult = Transformations.switchMap(_userList) { list ->
        var result = MutableLiveData<List<User>>()
//        list.forEach { remoteUser ->
//            _localUserList.value?.forEach { localUser ->
//                if (remoteUser.id == localUser.id) {
//                    remoteUser.isFavorite = true
//                }
//            }
//        }
        val localUser = _localUserList.value!!
        val userList = mutableListOf<User>()
        for (j in 0.._localUserList.value!!.size) {
            val localUser = localUser[j]

            for (i in 0..list.size) {
                var remoteUser = list[i]
                if (localUser.id == remoteUser.id) {
                    remoteUser.isFavorite = true
                    userList.add(remoteUser)
                }
            }
        }
        result.value = userList
        result
    }

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


    fun queryUserList(): LiveData<List<User>> {
        _localUserList.value = userRepository.queryUserLists().value
        return userRepository.queryUserLists()
//        val mediatorLiveData = MediatorLiveData<List<User>>()
//        mediatorLiveData.addSource(userRepository.queryUserLists()) {_localUserList.value = it}
//        _localUserList.value = mediatorLiveData.value
    }

    fun insertUser(user: User) {
        userRepository.addFavorite(user)
    }

    fun deleteUser(user: User) {
        userRepository.deleteFavorite(user)
    }
}