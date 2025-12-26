package com.example.painview.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.painview.ui.detail.MovieDetailArg
import com.example.painview.ui.detail.MovieDetailScreen
import com.example.painview.ui.movie.MoviesArg
import com.example.painview.ui.movie.MoviesScreen
import com.example.painview.ui.theme.PainViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            PainViewTheme {
                val navController = rememberNavController()
                NavHost(
                    navController,
                    startDestination = MoviesArg
                ) {
                    composable<MoviesArg> {
                        MoviesScreen {
                            navController.navigate(MovieDetailArg(it))
                        }
                    }
                    composable<MovieDetailArg> {
                        MovieDetailScreen()
                    }
                    composable("detail/{id}") {

                    }
                }
            }
        }
    }
}