package com.example.painview.ui.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.painview.R
import com.example.painview.constance.Constance
import com.example.painview.databinding.ItemListPopularMovieBinding
import com.example.painview.databinding.ItemTopRateMovieBinding
import com.example.painview.extension.loadFromUrl
import com.example.painview.ui.movie.MovieViewType

class MovieAdapter :
    ListAdapter<MovieViewType, RecyclerView.ViewHolder>(MovieDiffUtil()) {

    companion object {
        const val VIEW_POPULAR = 1
        const val VIEW_TOP_RATE = 2
    }

    var onItemClick: (Int?) -> Unit = {}

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item) {
            is MovieViewType.PopularMovie -> VIEW_POPULAR
            is MovieViewType.TopRateMovie -> VIEW_TOP_RATE
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_POPULAR -> PopularMovieViewHolder(
                ItemListPopularMovieBinding.inflate(
                    layoutInflater
                )
            )

            VIEW_TOP_RATE -> TopRateMovieViewHolder(ItemTopRateMovieBinding.inflate(layoutInflater), onItemClick)
            else -> error("Unknown viewType=$viewType")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (val item = getItem(position)) {
            is MovieViewType.PopularMovie -> (holder as PopularMovieViewHolder).bind(item)
            is MovieViewType.TopRateMovie -> (holder as TopRateMovieViewHolder).bind(item)
        }
    }

    class PopularMovieViewHolder(private val binding: ItemListPopularMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val popularAdapter = PopularMovieAdapter()

        init {
            binding.listMovie.apply {
                val space = context.resources.getDimensionPixelSize(R.dimen.spacing_8)
                addItemDecoration(PopularMovieRecyclerViewItemDecoration(space))
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = popularAdapter
                setHasFixedSize(true)
                isNestedScrollingEnabled = false
            }
        }

        fun bind(movies: MovieViewType.PopularMovie) {
            popularAdapter.submitList(movies.data)
        }
    }

    class TopRateMovieViewHolder(private val binding: ItemTopRateMovieBinding, private val onItemClick: (Int?) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MovieViewType.TopRateMovie) {
            binding.imageBackDrop.loadFromUrl("${Constance.IMAGE_BACK_DROP}${model.data.backdropPath.orEmpty()}")
            binding.textTitle.text = model.data.title
            binding.root.setOnClickListener {
                onItemClick(model.data.id)
            }
        }
    }

    class MovieDiffUtil : DiffUtil.ItemCallback<MovieViewType>() {
        override fun areItemsTheSame(
            oldItem: MovieViewType,
            newItem: MovieViewType
        ): Boolean {
            return when {
                oldItem is MovieViewType.PopularMovie && newItem is MovieViewType.PopularMovie -> true
                oldItem is MovieViewType.TopRateMovie && newItem is MovieViewType.TopRateMovie -> oldItem.data.id == newItem.data.id
                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: MovieViewType,
            newItem: MovieViewType
        ): Boolean {
            return oldItem == newItem
        }
    }
}