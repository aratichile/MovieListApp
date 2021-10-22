package com.example.anvisdigitalassignment.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.anvisdigitalassignment.data.model.MovieSearch
import com.example.anvisdigitalassignment.data.network.ApiInterface
import com.example.anvisdigitalassignment.data.network.SafeApiRequest
import com.example.anvisdigitalassignment.utils.State
import kotlinx.coroutines.flow.Flow


class HomeRepository(
    private val api: ApiInterface
) : SafeApiRequest() {

    private var _movieResponse : MutableLiveData<State<MovieSearch>> = MutableLiveData()
    val movieSearchResult: LiveData<State<MovieSearch>>

        get() = _movieResponse

    suspend fun getMovies(
        searchTitle: String,
        apiKey: String
    ): MovieSearch {

        return apiRequest { api.getSearchResultData(searchTitle, apiKey) }
    }

}