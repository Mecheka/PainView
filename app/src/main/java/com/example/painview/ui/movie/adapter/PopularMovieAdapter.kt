package com.example.painview.ui.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.painview.constance.Constance
import com.example.painview.data.model.movie.MovieResultResponse
import com.example.painview.databinding.ItemPopularMovieBinding
import com.example.painview.extension.loadFromUrl

class PopularMovieAdapter(private val onItemClick: (Int?) -> Unit) :
    ListAdapter<MovieResultResponse, PopularMovieAdapter.PopularMovieViewHolder>(
        PopularMovieDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularMovieViewHolder {
        return PopularMovieViewHolder(
            ItemPopularMovieBinding.inflate(
                LayoutInflater.from(parent.context)
            ), onItemClick
        )
    }

    override fun onBindViewHolder(
        holder: PopularMovieViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    class PopularMovieViewHolder(
        private val binding: ItemPopularMovieBinding,
        private val onItemClick: (Int?) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MovieResultResponse) {
            binding.imageBackDrop.loadFromUrl("${Constance.IMAGE_BACK_DROP}${model.backdropPath.orEmpty()}")
            binding.textTitle.text = model.title
            binding.root.setOnClickListener {
                onItemClick(model.id)
            }
        }
    }

    class PopularMovieDiffUtil : DiffUtil.ItemCallback<MovieResultResponse>() {
        override fun areItemsTheSame(
            oldItem: MovieResultResponse,
            newItem: MovieResultResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieResultResponse,
            newItem: MovieResultResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
}