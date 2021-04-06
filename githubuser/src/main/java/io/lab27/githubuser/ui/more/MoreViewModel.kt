package io.lab27.githubuser.ui.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.lab27.githubuser.datasource.remote.MenuItem
import io.lab27.githubuser.datasource.remote.MenuModel
import io.lab27.githubuser.datasource.remote.MoreRepository

class MoreViewModel(private val moreRepository: MoreRepository) : ViewModel() {
    private var _mainList = MutableLiveData<List<MenuModel>>()
    val mainList: LiveData<List<MenuModel>>
        get() = _mainList

    private var _settingList = MutableLiveData<List<MenuModel>>()
    val settingList: LiveData<List<MenuModel>>
        get() = _settingList

    private var _appList = MutableLiveData<List<MenuItem>>()
    val appList: LiveData<List<MenuItem>>
        get() = _appList

    fun init() {
        _mainList.value = moreRepository.fetchMainMenu()
        _settingList.value = moreRepository.fetchSettingMenu()
        _appList.value = moreRepository.fetchAppList()
    }
}