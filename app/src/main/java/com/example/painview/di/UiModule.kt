package com.example.painview.di

import com.example.painview.ui.movie.MoviesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::MoviesViewModel)
}