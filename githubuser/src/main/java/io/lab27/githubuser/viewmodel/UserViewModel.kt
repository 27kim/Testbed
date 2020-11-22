package io.lab27.githubuser.viewmodel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import io.lab27.githubuser.base.BaseViewModel
import io.lab27.githubuser.data.datasource.remote.UserRepository
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.network.api.UserApi
import io.lab27.githubuser.util.L
import io.lab27.githubuser.util.UserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class UserViewModel(private val userRepository: UserRepository) : BaseViewModel() {
    val userApi : UserApi by inject(named("userApi"))
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

    private val _finishState = MutableLiveData<Boolean>()
    val finishState: LiveData<Boolean>
        get() = _finishState

    private var _query = MutableLiveData<String>("")
    val query: LiveData<String>
        get() = _query

    fun setQuery(query: String) {
        if (_query.value != query) {
            _userList.value = emptyList()
            page = 1
        }
        _query.value = query

        fetchUserList()
    }

    private var page = 1

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

    fun fetchUserList() {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val queryStr = if (query.value == "") "google" else query.value!!

                val remoteUser = mutableListOf<User>().apply {
                    val list = _userList.value
                    list?.forEach { user ->
                        this.add(user)
                    }
                }

                remoteUser.addAll(async(Dispatchers.IO) {
                    userRepository.fetchUserListCoroutinesPaging(queryStr, page++)?.items
                }.await())

//                val remoteUser = async(Dispatchers.IO) {
//                    userRepository.fetchUserList_coroutines_p(queryStr, page)?.items
//                        ?: emptyList()
//                }.await()

                val localUser =
                    async(Dispatchers.IO) { userRepository.queryUserListsCoroutines() }.await()

                if (localUser.isNotEmpty()) {
                    localUser?.forEach { l ->
                        if (l.isFavorite) {
                            val idx = getUserIndex(remoteUser, l)
                            if (idx > -1) {
                                remoteUser[idx].isFavorite = true
                            }
                        }
                    }
                    _userList.value = remoteUser
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
                        userRepository.fetchUserListCoroutinesPaging(query, page++)?.items
                            ?: emptyList()
                    }.await()
                val localUser =
                    async(Dispatchers.IO) { userRepository.queryUserListsCoroutines() }.await()

                val list = arrayListOf<User>().apply {
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
            UserDataSource(userApi, "asdf")
        }
            .flow
            .cachedIn(viewModelScope)
}
