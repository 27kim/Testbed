package io.lab27.githubuser.viewmodel

import androidx.lifecycle.*
import io.lab27.githubuser.base.BaseViewModel
import io.lab27.githubuser.data.UserRepository
import io.lab27.githubuser.data.combineWith
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.network.UserResponse
import io.lab27.githubuser.util.L
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(private val userRepository: UserRepository) : BaseViewModel() {
    private var _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>>
        get() = _userList

    private var _localUserList = MutableLiveData<List<User>>()
    val localUserList: LiveData<List<User>>
        get() = _localUserList

    private var _remoteTemp = MutableLiveData<List<User>>()
    val remoteTemp: LiveData<List<User>>
        get() = _remoteTemp

    private var _testUser = MutableLiveData<List<User>>()
    val testUser: LiveData<List<User>>
        get() = _testUser

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    var mediatorLiveData = MediatorLiveData<List<User>>()


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

    val combinedResult = Transformations.switchMap(_userList) { list ->
        var result = MutableLiveData<List<User>>()
        list.forEach { remoteUser ->
            _localUserList.value?.forEach { localUser ->
                if (remoteUser.id == localUser.id) {
                    remoteUser.isFavorite = true
                }
            }
        }
        val localUser = list ?: emptyList()
        val userList = mutableListOf<User>()
        result.value = localUser
        result
    }

    fun fetchUserList(name: String) {
        val remote = userRepository.fetchUserList(name)
        val local = userRepository.queryUserLists()

        remote
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _isLoading.value = true }
            .doOnError { _isLoading.value = false }
            .doOnSuccess { _isLoading.value = false }
            .subscribe(
                { result -> _userList.value = result.items },
                { e ->
                    L.e("${e.message}")
                    _error.value = e.message
                }
            )
            .also { compositeDisposable.add(it) }
    }


    fun queryUserList(): LiveData<List<User>> {
        _localUserList.value = userRepository.queryUserLists().value
        return userRepository.queryUserLists()
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
        var list = mediatorLiveData.value
        list?.let {
            val idx = getUserIndex(user)
            if (idx > 0) {
                list[idx].isFavorite = user.isFavorite
            }
        }
        mediatorLiveData.value = list
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


    /**
     *
     *
     *
     *
     * temp
     * */
    fun getUserList(query: String) {
        _isLoading.value = true
        userRepository.fetchUserList_live(query).enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                L.i("getUserList - onFailure")
                _isLoading.value = false
            }

            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                L.e("getUserList - onResponse ${response.body()?.items}")
                _remoteTemp.value = response.body()?.items
                _isLoading.value = false
            }
        })

        val local = userRepository.queryUserLists()

        mediatorLiveData.addSource(local) {
            L.i("addSource(local)")
            mediatorLiveData.value = combine(local, _remoteTemp)
        }

        mediatorLiveData.addSource(_remoteTemp) {
            L.i("addSource(remote)")
            mediatorLiveData.value = combine(local, _remoteTemp)
        }

//        mediatorLiveData.removeSource(local)
//        mediatorLiveData.removeSource(_remoteTemp)
    }

  /*  fun getUserList(query: String) {
        _isLoading.value = true
        userRepository.fetchUserList_live(query).enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                L.i("getUserList - onFailure")
                _isLoading.value = false
            }

            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                L.e("getUserList - onResponse ${response.body()?.items}")
                _remoteTemp.value = response.body()?.items
                _isLoading.value = false

            }
        })

        val local = userRepository.queryUserLists()

        val tempList = local.combineWith(_remoteTemp) { l, r ->
            run {
                l?.forEachIndexed { idx, localUser ->
                    r?.forEachIndexed { i, remoteUser ->
                        if (localUser.id == remoteUser.id) {
                            remoteUser.isFavorite == true
                        }
                    }
                }
            }
            r
        }
        _testUser.value = tempList.value

//        mediatorLiveData.removeSource(local)
//        mediatorLiveData.removeSource(_remoteTemp)
    }*/


    private fun combine(
        local: LiveData<List<User>>,
        remoteTemp: LiveData<List<User>>
    ): List<User>? {
        L.e("start of combine")
        var mutableLiveData = MutableLiveData<List<User>>()

        var l = local.value?.toMutableList() ?: run { return emptyList() }
        var r = remoteTemp.value?.toMutableList() ?: run { return emptyList() }

        l.forEach {
            if (it.isFavorite) {
                val idx = getUserIndex(it)
                if (idx != -1) {
                    r[idx].isFavorite = true
                }
            }
        }
        mutableLiveData.value = r

        return mutableLiveData.value
    }

    fun getUserIndex(user: User): Int {
        var result = -1

        _remoteTemp.value?.forEachIndexed { idx, remote ->
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