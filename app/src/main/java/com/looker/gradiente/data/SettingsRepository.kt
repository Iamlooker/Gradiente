package com.looker.gradiente.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

data class Settings(
    val selectedGradient: Int
)

class SettingsRepository(
    private val dataStore: DataStore<Preferences>
) {

    private companion object PreferencesKey {
        val SWATCH = intPreferencesKey("selected_swatch")
    }

    val settingsFlow: Flow<Settings> = dataStore.data
        .catch { exception ->
            if (exception is IOException) Log.e("Settings", "Error reading preferences.", exception)
            else throw exception
        }
        .map(::mapToSettings)

    suspend fun fetchInitialPreferences() = settingsFlow.first()

    suspend fun setGradientId(id: Int) {
        dataStore.edit { preferences ->
            preferences[SWATCH] = id
        }
    }

    private fun mapToSettings(preferences: Preferences): Settings {
        val swatch = preferences[SWATCH] ?: -1
        return Settings(swatch)
    }

}