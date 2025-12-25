package com.example.painview.data.repository

import com.example.painview.data.api.MovieApi
import com.example.painview.data.model.detail.MovieDetailResponse
import com.example.painview.data.model.movie.MovieResultResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val api: MovieApi) {
    suspend fun getPopularMovie(): Result<List<MovieResultResponse>> = withContext(Dispatchers.IO) {
        runCatching { api.getPopularMovie().body()?.results.orEmpty() }
    }

    suspend fun getTopRateMovie(): Result<List<MovieResultResponse>> = withContext(Dispatchers.IO) {
        runCatching { api.getTopRateMovie().body()?.results.orEmpty() }
    }

    suspend fun getMovieDetail(id: Int): Result<MovieDetailResponse?> =
        withContext(Dispatchers.IO) {
            runCatching { api.getMovieDetail(id).body() }
        }
}