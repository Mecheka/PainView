package com.example.painview.ui.movie

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.painview.constance.Constance
import com.example.painview.data.model.movie.MovieResultResponse
import com.example.painview.ui.CommonState
import com.example.painview.ui.theme.PainViewTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoviesScreen(viewModel: MoviesViewModel = koinViewModel(), onItemClick: (Int) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MoviesScreen(state, onItemClick)
}

@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun MoviesScreen(state: CommonState<MovieViewType>, onItemClick: (Int) -> Unit) {
    Scaffold {
        when (state) {
            is CommonState.Error -> {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(state.message)
                }
            }

            CommonState.Loading -> {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            }

            is CommonState.Success<MovieViewType> -> {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .padding(
                                top = padding.calculateTopPadding(),
                                bottom = padding.calculateBottomPadding()
                            )
                            .fillMaxSize()
                    ) {
                        if (state.data.popular.isNotEmpty()) {
                            item {
                                PopularMoviesItems(state, onItemClick)
                            }
                        }

                        if (state.data.topRate.isNotEmpty()) {
                            items(state.data.topRate, key = { it.id!! }) {
                                TopRateMovieItem(it, onItemClick)
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun PopularMoviesItems(
    state: CommonState.Success<MovieViewType>,
    onItemClick: (Int) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(
            state.data.popular,
            key = { it.id!! }) {
            Column(modifier = Modifier.clickable {
                onItemClick(it.id!!)
            }) {
                GlideImage(
                    model = "${Constance.IMAGE_BACK_DROP}${it.backdropPath.orEmpty()}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .height(180.dp)
                        .aspectRatio(16f / 9f),
                    contentDescription = null
                )
                Text(it.title.orEmpty(), modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun TopRateMovieItem(movie: MovieResultResponse, onItemClick: (Int) -> Unit) {
    Row(
        Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .clickable {
                onItemClick(movie.id!!)
            }
    ) {
        GlideImage(
            model = "${Constance.IMAGE_BACK_DROP}${movie.backdropPath.orEmpty()}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .width(180.dp)
                .aspectRatio(16f / 9f),
            contentDescription = null
        )
        Text(
            movie.title.orEmpty(),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewTopRateItem() {
    PainViewTheme {
        TopRateMovieItem(
            MovieResultResponse(
                title = "Title",
                backdropPath = "/84h6avqHvW2TgLfERuqYkQwzjp1.jpg"
            )
        ) {}
    }
}