package com.example.painview.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.painview.constance.Constance
import com.example.painview.data.model.detail.MovieDetailResponse
import com.example.painview.databinding.FragmentMovieDetailBinding
import com.example.painview.extension.loadFromUrl
import com.example.painview.ui.CommonState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding: FragmentMovieDetailBinding
        get() = _binding!!
    private val viewModel: MovieDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is CommonState.Error -> {
                            with(binding) {
                                loading.isVisible = false
                                imagePoster.isVisible = false
                                textTitle.isVisible = false
                                textOverview.isVisible = false
                                textError.isVisible = true
                                textError.text = state.message
                            }
                        }

                        CommonState.Loading -> {
                            with(binding) {
                                loading.isVisible = true
                                imagePoster.isVisible = false
                                textTitle.isVisible = false
                                textOverview.isVisible = false
                                textError.isVisible = false
                            }
                        }

                        is CommonState.Success<MovieDetailResponse> -> {
                            with(binding) {
                                loading.isVisible = false
                                textError.isVisible = false
                                imagePoster.isVisible = true
                                imagePoster.loadFromUrl("${Constance.IMAGE_URL}${state.data.posterPath.orEmpty()}")
                                textTitle.isVisible = true
                                textTitle.text = state.data.title
                                textOverview.isVisible = true
                                textOverview.text = state.data.overview
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