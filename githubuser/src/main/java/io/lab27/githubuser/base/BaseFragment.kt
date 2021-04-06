package io.lab27.githubuser.base

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment(){
    var isLoading = false
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
