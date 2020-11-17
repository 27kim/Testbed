package io.lab27.githubuser.viewmodel

import androidx.lifecycle.*
import io.lab27.githubuser.data.AuthRepository
import io.lab27.githubuser.data.model.EventResponse
import kotlinx.coroutines.launch

class MHViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _event = MutableLiveData<List<EventResponse>>()
    val event: LiveData<List<EventResponse>>
        get() = _event

    fun fetchEvent() {
        viewModelScope.launch {
            _event.value = authRepository.fetchEvent()
            authRepository.fetchEvent().map {

            }
        }
    }
}
