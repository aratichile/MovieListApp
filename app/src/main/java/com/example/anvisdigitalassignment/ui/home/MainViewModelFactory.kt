package com.example.anvisdigitalassignment.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anvisdigitalassignment.data.repository.HomeRepository
import java.lang.Appendable

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val repository: HomeRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}