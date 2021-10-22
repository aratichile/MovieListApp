package com.example.anvisdigitalassignment.ui.home

import androidx.lifecycle.*
import com.example.anvisdigitalassignment.data.model.MovieSearch
import com.example.anvisdigitalassignment.data.model.Search
import com.example.anvisdigitalassignment.data.repository.HomeRepository
import com.example.anvisdigitalassignment.utils.ApiException
import com.example.anvisdigitalassignment.utils.AppConstant
import com.example.anvisdigitalassignment.utils.NoInternetException
import kotlinx.coroutines.*

class MainViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    private lateinit var job : Job

    private lateinit var movieResponse: MovieSearch

    private var movieList = ArrayList<Search?>()

    private val _moviesLiveData = MutableLiveData<ArrayList<Search?>>()
        val moviesLiveData: LiveData<ArrayList<Search?>>
        get() = _moviesLiveData

    private val _movieItemLiveData = MutableLiveData<Search?>()
    val movieItemLiveData: LiveData<Search?>
        get() = _movieItemLiveData

    private val _movieNameLiveData = MutableLiveData<String>()
    val movieNameLiveData: LiveData<String>
        get() = _movieNameLiveData

    private val _loadMoreListLiveData = MutableLiveData<Boolean>()
    val loadMoreListLiveData: LiveData<Boolean>
        get() = _loadMoreListLiveData

    fun getMoviesData() {


        viewModelScope.launch(Dispatchers.IO) {

            if ( _movieNameLiveData.value != null && _movieNameLiveData.value!!.isNotEmpty()) {
                try {
                    movieResponse = repository.getMovies(
                        _movieNameLiveData.value!!,
                        AppConstant.API_KEY
                    )
                    withContext(Dispatchers.Main) {
                        if (movieResponse.Response == AppConstant.SUCCESS) {
                            movieList.addAll(movieResponse.Search)
                            _moviesLiveData.postValue(movieList)
                            _loadMoreListLiveData.value = false
                        }
                    }
                } catch (e: ApiException) {
                    withContext(Dispatchers.Main) {
//                        _moviesLiveData.postValue(movieResponse.totalResults)
                        _loadMoreListLiveData.value = false
                    }
                } catch (e: NoInternetException) {
                    withContext(Dispatchers.Main) {
//                        _moviesLiveData.postValue(State.error(e.message!!))
                        _loadMoreListLiveData.value = false
                    }
                }
            }
        }
    }

    fun searchMovie(query: String) {
        _movieNameLiveData.value = query
    }

    fun loadMore() {
        getMoviesData()
    }

    fun movieItem(movie: Search) {
        _movieItemLiveData.value = movie
    }
}