package io.lab27.githubuser.ui.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.lab27.githubuser.data.datasource.remote.MoreRepository

class MoreViewModel(private val moreRepository: MoreRepository) : ViewModel() {
    private var _settingList = MutableLiveData<List<String>>()
    val settingList: LiveData<List<String>>
        get() = _settingList

    private var _usageList = MutableLiveData<List<String>>()
    val usageList: LiveData<List<String>>
        get() = _usageList
    fun init() {
        _settingList.value = moreRepository.fetchSettingMenu()
        _usageList.value = moreRepository.fetchUsageMenu()
    }
}