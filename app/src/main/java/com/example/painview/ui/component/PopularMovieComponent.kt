package com.example.painview.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.painview.data.model.movie.MovieResultResponse
import com.example.painview.databinding.ItemPopularMovieBinding

class PopularMovieComponent @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private val binding: ItemPopularMovieBinding by lazy {
        ItemPopularMovieBinding.inflate(
            LayoutInflater.from(context)
        )
    }

    init {
        addView(binding.root)
    }

    fun bind(model: MovieResultResponse) {

    }
}