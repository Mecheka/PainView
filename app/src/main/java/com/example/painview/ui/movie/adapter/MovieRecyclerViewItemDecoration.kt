package com.example.painview.ui.movie.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MovieRecyclerViewItemDecoration(
    private val verticalSpace: Int,
    private val horizontalSpace: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val adapter = parent.adapter as MovieAdapter
        when (adapter.getItemViewType(position)) {
            MovieAdapter.VIEW_POPULAR -> {
                outRect.top = verticalSpace
                outRect.bottom = verticalSpace
            }

            MovieAdapter.VIEW_TOP_RATE -> {
                outRect.top = verticalSpace
                outRect.bottom = verticalSpace
                outRect.right = horizontalSpace
                outRect.left = horizontalSpace
            }
        }
    }
}