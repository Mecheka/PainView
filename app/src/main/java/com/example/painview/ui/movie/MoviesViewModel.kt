package com.example.painview.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.painview.data.repository.MovieRepository
import com.example.painview.ui.CommonState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class MoviesViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _state =
        MutableStateFlow<CommonState<List<MovieViewType>>>(CommonState.Loading)
    val state: StateFlow<CommonState<List<MovieViewType>>> = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = CommonState.Loading
            supervisorScope {
                val popularDeferred = async { repository.getPopularMovie().getOrNull() }
                val topRateDeferred = async { repository.getTopRateMovie().getOrNull() }
                val items = buildList {
                    val popular = popularDeferred.await()
                    val topRate = topRateDeferred.await()
                    if (!popular.isNullOrEmpty()) {
                        add(MovieViewType.PopularMovie(popular))
                    }
                    if (!topRate.isNullOrEmpty()) {
                        addAll(topRate.map { MovieViewType.TopRateMovie(it) })
                    }
                }
                if (items.isEmpty()) {
                    _state.value = CommonState.Error("Unable to load movies")
                } else {
                    _state.value = CommonState.Success(items)
                }
            }
        }
    }
}
