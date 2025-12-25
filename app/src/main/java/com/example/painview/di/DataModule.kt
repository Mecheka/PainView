package com.example.painview.di

import com.example.painview.data.api.MovieApi
import com.example.painview.data.repository.MovieRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val dataModule = module {
    single { get<Retrofit>().create<MovieApi>() }
    factoryOf(::MovieRepository)
}