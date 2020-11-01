package io.lab27.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.*
import io.lab27.githubuser.base.BaseViewModel
import io.lab27.githubuser.data.UserRepository
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.util.L
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import retrofit2.HttpException

class UserViewModel(private val userRepository: UserRepository) : BaseViewModel() {
    private var _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>>
        get() = _userList

    private var _localUserList = MutableLiveData<List<User>>()
    val localUserList: LiveData<List<User>>
        get() = _localUserList

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val backPressSubject =
        BehaviorSubject.createDefault(0L)

    //Ext
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


//                        if (e instanceof HttpException) {
//                            HttpException exception = (HttpException) e;
//                            Response response = exception.response();
//                            try {
//                                JSONObject jsonObject = new  JSONObjec(response.errorBody().string());
//                                Log.e("Error ","" + jsonObject.optString("message"));
//                            } catch (JSONException e1) {
//                                e1.printStackTrace();
//                            } catch (IOException e1) {
//                                e1.printStackTrace();
//                            }
//                        }
                }
            )
            .also { compositeDisposable.add(it) }
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

    override fun clearDisposable() {
        compositeDisposable.clear()
    }
}