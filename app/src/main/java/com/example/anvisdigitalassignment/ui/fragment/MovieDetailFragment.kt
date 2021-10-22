package com.example.anvisdigitalassignment.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.anvisdigitalassignment.R
import com.example.anvisdigitalassignment.databinding.FragmentMovieDetailBinding
import com.example.anvisdigitalassignment.ui.home.MainViewModel
import com.example.anvisdigitalassignment.ui.home.MainViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MovieDetailFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var viewModel: MainViewModel
    private val factory: MainViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)
        viewModel = ViewModelProvider(requireActivity(), factory).get(MainViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupUI()

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(requireActivity(), factory).get(MainViewModel::class.java)

    }

    private fun setupUI() {
        viewModel.getMoviesData()

        viewModel.movieItemLiveData.observe(viewLifecycleOwner, Observer { it ->

            binding.txtMovieName.text = it?.Title
            binding.txtYear.text = "Release Year :${it?.Year}"
            Glide.with(binding.imgPoster).load(it?.Poster)
                .centerCrop()
                .thumbnail(0.5f)
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgPoster)

        })

    }


}