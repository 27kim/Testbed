package com.deliveryhero.githubuser

import androidx.lifecycle.*
import com.deliveryhero.githubuser.network.User

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository.getInstance()
    private val queryId = MutableLiveData<String>()
    private val userIdFromDb = MutableLiveData<String>()
    val requestAllUser = MutableLiveData<Lifecycle.Event>()

    val userData: LiveData<List<User>> = Transformations.switchMap(queryId) { userId ->
        userRepository.getUserData(userId)
    }

    val userDataFromDb : LiveData<User> = Transformations.switchMap(userIdFromDb){userId ->
        userRepository.getUserFromDb(userId)
    }

    val getAllUsers = Transformations.switchMap(requestAllUser){
        userRepository.getAllUserFromDb()
    }

    fun fetchUserFromApi(user: String) {
        queryId.value = user
    }

    fun fetchUserFromDb(user: String) {
        userIdFromDb.value = user
    }

    fun modifyUser(user: User) {
        user.isFavorite = !user.isFavorite

        if (user.isFavorite) {
            userRepository.addUser(user)
        } else {
            userRepository.removeUser(user)
        }
    }

    fun getUserFromDb(): LiveData<List<User>> {
        return userRepository.getAllUserFromDb()
    }
}