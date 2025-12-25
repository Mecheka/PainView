package com.example.painview.extension

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadFromUrl(url: String) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .into(this)
}