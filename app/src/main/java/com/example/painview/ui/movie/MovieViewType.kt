package com.example.painview.ui.movie

import com.example.painview.data.model.movie.MovieResultResponse

data class MovieViewType(
    val popular: List<MovieResultResponse>,
    val topRate: List<MovieResultResponse>
)