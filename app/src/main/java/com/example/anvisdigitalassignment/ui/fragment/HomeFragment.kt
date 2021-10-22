package com.example.anvisdigitalassignment.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.anvisdigitalassignment.R
import com.example.anvisdigitalassignment.databinding.FragmentHomeBinding
import com.example.anvisdigitalassignment.ui.home.MainViewModel
import com.example.anvisdigitalassignment.ui.home.MainViewModelFactory
import com.example.anvisdigitalassignment.utils.dismissKeyboard
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment(), KodeinAware {

    override val  kodein by kodein()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MainViewModel
    private val factory : MainViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProvider(requireActivity(),factory).get(MainViewModel::class.java)

        return binding.root
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            setupViewModel()
            onAttach(requireContext())

        }

        private fun setupViewModel() {
            viewModel = ViewModelProvider(requireActivity(), factory).get(MainViewModel::class.java)

        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycle?.addObserver(ActivityLifeCycleObserver {
            binding.searchView.apply {
                queryHint = "Search"
                isSubmitButtonEnabled = true
                onActionViewExpanded()
            }

            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(query: String): Boolean {
                        activity!!.dismissKeyboard(binding.searchView)
                        binding.searchView.clearFocus()

                        viewModel.searchMovie(query)
                        findNavController().navigate(R.id.movieListFragment)

                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        Log.e("TAG","Inside txt change")
                        return false
                    }

                })

        })
    }
}