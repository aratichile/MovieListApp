package com.example.anvisdigitalassignment.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anvisdigitalassignment.R
import com.example.anvisdigitalassignment.data.model.Search
import com.example.anvisdigitalassignment.databinding.FragmentMovieListBinding
import com.example.anvisdigitalassignment.ui.adapter.CustomAdapterMovies
import com.example.anvisdigitalassignment.ui.home.MainViewModel
import com.example.anvisdigitalassignment.ui.home.MainViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MovieListFragment : Fragment(), KodeinAware, CustomAdapterMovies.OnItemClickListener {

    override val  kodein by kodein()
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var customAdapterMovies: CustomAdapterMovies
    private val factory : MainViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        viewModel = ViewModelProvider(requireActivity(),factory).get(MainViewModel::class.java)


        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupUI()
        initializeObserver()

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(requireActivity(), factory).get(MainViewModel::class.java)

    }

    private fun setupUI() {
        viewModel.getMoviesData()

        viewModel.moviesLiveData.observe(viewLifecycleOwner, Observer { movies ->

            customAdapterMovies = CustomAdapterMovies(movies, this)
            binding.recyclerViewMovie.apply {
                val gridLayoutManager = GridLayoutManager(requireContext(), 2)
                gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
                layoutManager = gridLayoutManager
                itemAnimator = DefaultItemAnimator()
                adapter = customAdapterMovies

            }

        })

    }

    private fun initializeObserver() {
        viewModel.movieNameLiveData.observe(viewLifecycleOwner, Observer {
            Log.i("Info", "Movie Name = $it")
        })
        viewModel.loadMoreListLiveData.observe(viewLifecycleOwner, Observer {
            if (it) {
                customAdapterMovies.setData(null)
                Handler().postDelayed({
                    viewModel.loadMore()
                }, 2000)
            }
        })
    }

    override fun onItemClicked(movie: Search) {

        viewModel.movieItem(movie)
        findNavController().navigate(R.id.movieDetailsFragment)

    }

}