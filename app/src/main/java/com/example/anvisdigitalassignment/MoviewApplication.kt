package com.example.anvisdigitalassignment

import android.app.Application
import com.example.anvisdigitalassignment.data.network.ApiInterface
import com.example.anvisdigitalassignment.data.network.NetworkConnectionInterceptor
import com.example.anvisdigitalassignment.data.repository.HomeRepository
import com.example.anvisdigitalassignment.ui.home.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MovieApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MovieApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { ApiInterface(instance()) }
        bind() from singleton { HomeRepository(instance()) }
        bind() from provider { MainViewModelFactory(instance()) }

    }
}