package io.lab27.githubuser.ui.date

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.lab27.githubuser.datasource.local.PreferenceRepository
import io.lab27.githubuser.util.L
import kotlinx.coroutines.launch
import java.util.*

class DateFilterViewModel(val preferenceRepository: PreferenceRepository) : ViewModel() {
    private val _startDate = MutableLiveData(setDate(getPreference()))
    val startDate: LiveData<Date>
        get() = _startDate

    private val _endDate = MutableLiveData(setDate())
    val endDate: LiveData<Date>
        get() = _endDate

    private fun setDate(amount: Int = 0): Date =
        Calendar.getInstance().apply { add(Calendar.MONTH, amount) }.time

    fun setStartDate(amount: Int) {
        _startDate.value = Calendar.getInstance().apply { add(Calendar.MONTH, amount) }.time
        updatePreference(amount)
    }

    private fun updatePreference(amount: Int) {
        viewModelScope.launch {
            preferenceRepository.updateDatePreference(amount)
        }
    }

    private fun getPreference(): Int {
        var result = 0
        viewModelScope.launch {
            result = preferenceRepository.getDatePreference()
        }
        return result
    }
}