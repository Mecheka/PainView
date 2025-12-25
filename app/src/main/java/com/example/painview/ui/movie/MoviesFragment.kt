package com.example.painview.ui.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.painview.R
import com.example.painview.databinding.FragmentMainBinding
import com.example.painview.ui.CommonState
import com.example.painview.ui.movie.adapter.MovieAdapter
import com.example.painview.ui.movie.adapter.MovieRecyclerViewItemDecoration
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!
    private val viewModel: MoviesViewModel by viewModel()
    private val movieAdapter: MovieAdapter by lazy { MovieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.movieList.apply {
            val verticalSpace = resources.getDimensionPixelSize(R.dimen.spacing_8)
            val horizontalSpace = resources.getDimensionPixelSize(R.dimen.spacing_16)
            addItemDecoration(MovieRecyclerViewItemDecoration(verticalSpace,horizontalSpace))
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            this.adapter = movieAdapter
        }
        movieAdapter.onItemClick = {
            findNavController().navigate("app://detail/$it".toUri())
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is CommonState.Error -> {
                            with(binding) {
                                progressBar.isVisible = false
                                movieList.isVisible = false
                                textError.isVisible = true
                                textError.text = state.message
                            }
                        }

                        CommonState.Loading -> {
                            with(binding) {
                                progressBar.isVisible = true
                                textError.isVisible = false
                                movieList.isVisible = false
                            }
                        }

                        is CommonState.Success<List<MovieViewType>> -> {
                            with(binding) {
                                progressBar.isVisible = false
                                textError.isVisible = false
                                movieList.isVisible = true
                                movieAdapter.submitList(state.data)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}