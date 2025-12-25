package com.example.painview.data.api

import com.example.painview.data.model.detail.MovieDetailResponse
import com.example.painview.data.model.movie.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("discover/movie?include_adult=false&include_video=false&language=th-TH&page=1&sort_by=popularity.desc")
    suspend fun getPopularMovie(): Response<MovieResponse>

    @GET("discover/movie?include_adult=false&include_video=false&language=th-TH&page=1&sort_by=vote_average.desc&without_genres=99,10755&vote_count.gte=200")
    suspend fun getTopRateMovie(): Response<MovieResponse>

    @GET("movie/{id}?language=th-TH")
    suspend fun getMovieDetail(
        @Path("id") id: Int
    ): Response<MovieDetailResponse>
}