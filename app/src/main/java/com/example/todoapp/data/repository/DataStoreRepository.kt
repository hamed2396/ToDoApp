package com.example.todoapp.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.todoapp.data.models.Priority
import com.example.todoapp.data.repository.DataStoreRepository.PreferencesKey.sortKey
import com.example.todoapp.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = Constants.PREFERENCES_NAME)
@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext context: Context) {
    private object PreferencesKey {

        val sortKey = stringPreferencesKey(Constants.PREFERENCES_KEY)
    }

    private val dataStore = context.dataStore
    suspend fun persistSortState(priority: Priority) {
        dataStore.edit {
            it[PreferencesKey.sortKey] = priority.name
        }
    }

    val readSortSate = dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else throw it
    }.map {
     it[sortKey] ?: Priority.NONE


    }
}