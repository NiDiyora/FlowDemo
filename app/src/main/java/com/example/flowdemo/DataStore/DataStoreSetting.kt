package com.example.flowdemo.DataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreSetting
@Inject
constructor(@ApplicationContext private val context: Context) {

    object Preferencekey {
        val name = stringPreferencesKey("name")
    }

    private val Context.dataStore by preferencesDataStore(name = "datastore")

    suspend fun writeToLocal(name: String) {
        context.dataStore.edit {
            it[Preferencekey.name] = name
        }
    }

    val readToLocal: Flow<String> = context.dataStore.data
        .catch {
            if (this is Exception) {
                emit(emptyPreferences())
            }
        }.map {
            val name = it[Preferencekey.name] ?: ""
            return@map name
        }
}