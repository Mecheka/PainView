package com.example.painview.ui.movie.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PopularMovieRecyclerViewItemDecoration(private val space: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        when (position) {
            0 -> {
                outRect.right = space
            }
            (parent.adapter?.itemCount!! - 1) -> {
                outRect.left = space
            }
            else -> {
                outRect.left=space
                outRect.right=space
            }
        }
    }
}