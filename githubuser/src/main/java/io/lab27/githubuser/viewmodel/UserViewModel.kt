package io.lab27.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.lab27.githubuser.base.BaseViewModel
import io.lab27.githubuser.data.UserRepository
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.util.L
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : BaseViewModel() {
    private var _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>>
        get() = _userList

    private var _localUserList = userRepository.queryAllUsers()
    val localUserList: LiveData<List<User>>
        get() = _localUserList

    private var _remoteTemp = MutableLiveData<List<User>>()
    val remoteTemp: LiveData<List<User>>
        get() = _remoteTemp

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    //mediatorLiveData 샘플
    var mediatorLiveData = MediatorLiveData<List<User>>()

    private var _coroutinesUser = MutableLiveData<List<User>?>()
    val coroutinesUser: LiveData<List<User>?>
        get() = _coroutinesUser

    private val backPressSubject =
        BehaviorSubject.createDefault(0L)

    private val _finishState = MutableLiveData<Boolean>()
    val finishState: LiveData<Boolean>
        get() = _finishState

    init {
        backPressSubject
            .toFlowable(BackpressureStrategy.BUFFER)
            .observeOn(AndroidSchedulers.mainThread())
            .buffer(2, 1)
            .map { it[0] to it[1] }
            .subscribe(
                {
                    _finishState.value = it.second - it.first < TOAST_DURATION
                },
                {
                    _error.value = it.message
                }
            ).also { compositeDisposable.add(it) }
    }

    fun updateUser(user: User) {
        L.i("is favorite ? ${user.isFavorite} / user : $user")
        when (user.isFavorite) {
            true -> insertUser(user)
            false -> deleteUser(user)
        }
        updateUserFavorite(user)
    }

    private fun updateUserFavorite(user: User) {
        var list = _coroutinesUser.value
        list?.let {
            val idx = getUserIndex(user)
            if (idx > -1) {
                list[idx].isFavorite = user.isFavorite
            }
        }
        _coroutinesUser.value = list
    }

    private fun insertUser(user: User) {
        userRepository.addFavorite(user)
    }

    private fun deleteUser(user: User) {
        userRepository.deleteFavorite(user)
    }

    override fun clearDisposable() {
        compositeDisposable.clear()
    }

    fun getUserList_courotines(query: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val remoteUser = userRepository.fetchUserList_coroutines(query).items
                val localUser = localUserList.value

                val resultList = mutableListOf<User>()

                localUser?.forEach { l ->
                    if (l.isFavorite) {
                        val idx = getUserIndex(l)
                        if (idx != -1) {
                            remoteUser[idx].isFavorite = true
                        }
                    }
                }

                _coroutinesUser.value = remoteUser
            } catch (e: Exception) {
                L.e("getUserList_courotines : ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getUserIndex(user: User): Int {
        var result = -1

        _coroutinesUser.value?.forEachIndexed { idx, remote ->
            if (remote.id == user.id) {
                return idx
            }
        }
        return result
    }


    /**
     *
     *
     *
     *
     * temp
     * */
}