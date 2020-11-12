package io.lab27.githubuser.viewmodel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import io.lab27.githubuser.base.BaseViewModel
import io.lab27.githubuser.data.UserRepository
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.data.datasource.remote.RemoteDataSourceImpl
import io.lab27.githubuser.util.L
import io.lab27.githubuser.util.UserDataSource
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : BaseViewModel() {
    private var _localUserList = userRepository.queryAllUsers()
    val localUserList: LiveData<List<User>>
        get() = _localUserList

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    //mediatorLiveData 샘플
    var mediatorLiveData = MediatorLiveData<List<User>>()

    private var _userList = MutableLiveData<List<User>?>()
    val userList: LiveData<List<User>?>
        get() = _userList

    private val backPressSubject =
        BehaviorSubject.createDefault(0L)

    private val _finishState = MutableLiveData<Boolean>()
    val finishState: LiveData<Boolean>
        get() = _finishState

    private var _query = MutableLiveData<String>()
    val query: LiveData<String>
        get() = _query

    private var page = 1
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
        var list = _userList.value
        list?.let {
            val idx = getUserIndex(list, user)
            if (idx > -1) {
                list[idx].isFavorite = user.isFavorite
            }
        }
        _userList.value = list
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

    fun fetchUserListCoroutines(query: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                /**
                 * async 사용
                 * */
                val remoteUser =
                    async(Dispatchers.IO) {
                        userRepository.fetchUserList_coroutines(query)?.items ?: emptyList()
//                        userRepository.fetchUserList_coroutines(query)?.items ?: emptyList()
                    }.await()
                val localUser =
                    async(Dispatchers.IO) { userRepository.queryUserLists_coroutines() }.await()

                if (localUser.isNotEmpty()) {
                    localUser?.forEach { l ->
                        if (l.isFavorite) {
                            val idx = getUserIndex(remoteUser, l)
                            if (idx > -1) {
                                remoteUser[idx].isFavorite = true
                            }
                        }
                        _userList.value = remoteUser
                    }
                } else {
                    _userList.value = remoteUser
                }
            } catch (t: Throwable) {
                _isLoading.value = false
                _error.value = t.message
                L.e("fetchUserListCoroutines catch : ${t.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchUserListCoroutines_paging(query: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                /**
                 * async 사용
                 * */
                val remoteUser =
                    async(Dispatchers.IO) {
                        userRepository.fetchUserList_coroutines_p(query, page++)?.items ?: emptyList()
                    }.await()
                val localUser =
                    async(Dispatchers.IO) { userRepository.queryUserLists_coroutines() }.await()

                val list = arrayListOf<User>().apply{
                    addAll(_userList.value ?: arrayListOf())
                }

                list.addAll(remoteUser)

                if (localUser.isNotEmpty()) {
                    localUser?.forEach { l ->
                        if (l.isFavorite) {
                            val idx = getUserIndex(list, l)
                            if (idx > -1) {
                                list[idx].isFavorite = true
                            }
                        }
                    }
                    _userList.value = list
                } else {
                    _userList.value = list
                }
            } catch (t: Throwable) {
                _isLoading.value = false
                _error.value = t.message
                L.e("fetchUserListCoroutines catch : ${t.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * launch using viewModelScopte sample
     * */
    fun getData(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                block()
            } catch (t: Throwable) {
                _isLoading.value = false
                _error.value = t.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getUserIndex(remoteUser: List<User>, user: User): Int {
        var result = -1

        remoteUser.forEachIndexed { idx, remote ->
            if (remote.id == user.id) {
                return idx
            }
        }
        return result
    }

    val listData =
        Pager(PagingConfig(pageSize = 15)) {
            UserDataSource(RemoteDataSourceImpl(), "asdf")
        }
            .flow
            .cachedIn(viewModelScope)

    /**
     * temp
     * */
    private val tempList = Transformations.map(query) {
        fetchUserListCoroutines(it)
    }
}
