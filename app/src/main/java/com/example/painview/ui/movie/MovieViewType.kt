package com.example.painview.ui.movie

import com.example.painview.data.model.movie.MovieResultResponse

sealed interface MovieViewType {
    data class PopularMovie(val data: List<MovieResultResponse>) : MovieViewType
    data class TopRateMovie(val data: MovieResultResponse) : MovieViewType
}