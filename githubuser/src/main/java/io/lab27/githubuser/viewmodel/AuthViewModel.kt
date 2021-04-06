package io.lab27.githubuser.viewmodel

import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.lab27.githubuser.datasource.remote.AuthRepository
import io.lab27.githubuser.data.model.TokenResponse
import kotlinx.coroutines.launch
import kotlin.collections.HashMap
import kotlin.collections.set

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _token = MutableLiveData<String>()
    val token: LiveData<String>
        get() = _token

    private val _me = MutableLiveData<String>()
    val me: LiveData<String>
        get() = _me

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun fetchAuthCode(authCode: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val tokenResponse = fetchToken(authCode)
            _token.value = tokenResponse.access_token
            val fetchMe = authRepository.fetchMe("Bearer ${tokenResponse.access_token}")
            _me.value = fetchMe.toString()

            _isLoading.value = false
        }
    }

    private suspend fun fetchToken(authCode: String): TokenResponse {
        val userId = "SkKblE82jiT6y6e356dCVy3q"
        val password = "JDJERbFBocSrZGL0BpEAmgBXp9dcfGzQHDuZg5xHqKtGqOua"
        val authPayload = "$userId:$password"

        val base64 = Base64.encodeToString(authPayload.toByteArray(), Base64.NO_WRAP)
        val header = "Basic $base64"

        val body = HashMap<String, String>()
        body["grant_type"] = "authorization_code"
        body["scope"] = "profile"
        body["code"] = authCode

        return authRepository.fetchToken(header, body)
    }
}
