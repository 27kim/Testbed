package io.lab27.githubuser.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import io.lab27.githubuser.util.L
import kotlinx.coroutines.flow.collect
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DataStoreSource(val context: Context) {
    private val Context.dataStore by preferencesDataStore(
        name = DATE_FILTER_PREFERENCES
    )

    fun create(): DataStore<Preferences> = context.dataStore

    companion object {
        private const val DATE_FILTER_PREFERENCES = "date_filter_preferences"
    }
}

interface PreferenceRepository {
    suspend fun getDatePreference(): Int
    suspend fun updateDatePreference(amount: Int)
}

class PreferenceRepositoryImpl : PreferenceRepository, KoinComponent {
    val context: Context by inject()
    private val Context.dataStore by preferencesDataStore(
        name = DATE_FILTER_PREFERENCES
    )

    private val dataStore = context.dataStore

    override suspend fun getDatePreference(): Int {
        var currentSetting = 0
        dataStore.data.collect {
            currentSetting = it[intPreferencesKey(DATE_FILTER_PREFERENCES)] ?: -3
            L.i("Preference getPreference $currentSetting")
        }
        return currentSetting
    }

    override suspend fun updateDatePreference(amount: Int) {
        L.i("Preference updatePreference $amount")

        dataStore.edit { preferences ->
            preferences[intPreferencesKey(DATE_FILTER_PREFERENCES)] = amount
        }
    }

    companion object {
        private const val DATE_FILTER_PREFERENCES = "date_filter_preferences"
    }
}