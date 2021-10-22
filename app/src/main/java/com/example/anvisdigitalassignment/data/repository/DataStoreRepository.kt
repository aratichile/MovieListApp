package com.example.anvisdigitalassignment.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val PREFERENCE_NAME = "my_preference"

class DataStoreRepository(context: Context) {

    private object PreferenceKeys{
        val movieName = preferencesKey<String>("movie_name")
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCE_NAME
    )

    suspend fun saveToDataStore(movieName : String){
        dataStore.edit {preference ->
            preference[PreferenceKeys.movieName] = movieName
        }
    }

    val readFromDataStore: Flow<String> = dataStore.data
        .catch { exception->
            if(exception is IOException){
                Log.d("dataStore", exception.message.toString())
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map { preference->
            val movieName = preference[PreferenceKeys.movieName] ?: "none"
            movieName
        }
}