package com.example.painview.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.painview.data.model.detail.MovieDetailResponse
import com.example.painview.data.repository.MovieRepository
import com.example.painview.ui.CommonState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: MovieRepository
) : ViewModel() {
    private val arg = savedStateHandle.toRoute<MovieDetailArg>()
    private val _state = MutableStateFlow<CommonState<MovieDetailResponse>>(CommonState.Loading)
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = CommonState.Loading
            repository.getMovieDetail(arg.id).onSuccess {
                _state.value = CommonState.Success(it!!)
            }.onFailure {
                _state.value = CommonState.Error(it.message.orEmpty())
            }
        }
    }
}