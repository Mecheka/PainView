package com.example.painview.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.painview.data.model.detail.MovieDetailResponse
import com.example.painview.ui.CommonState
import com.example.painview.ui.theme.PainViewTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MovieDetailScreen(viewModel: MovieDetailViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MovieDetailScreen(state)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MovieDetailScreen(state: CommonState<MovieDetailResponse>) {
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

        is CommonState.Success<MovieDetailResponse> -> {
            Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top = padding.calculateTopPadding())
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    GlideImage(
                        model = "${Constance.IMAGE_BACK_DROP}${state.data.posterPath.orEmpty()}",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .width(140.dp)
                            .aspectRatio(9f / 16f)
                    )
                    Text(
                        state.data.title.orEmpty(),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                    )
                    Text(state.data.overview.orEmpty(), modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewMovieDetailScreen() {
    PainViewTheme {
        MovieDetailScreen(
            CommonState.Success(
                MovieDetailResponse(
                    posterPath = "/7AfBMebJS8mEtSV5ymdxEPpgvXb.jpg",
                    title = "อวตาร: อัคนีและธุลีดิน",
                    overview = "ท่ามกลางสงครามอันโหดร้ายกับ RDA และความโศกเศร้าจากการสูญเสียลูกชายคนโต เจค ซัลลี่ และ เนย์ทีรี จะต้องพบกับภัยคุกคามใหม่บนดาวแพนโดร่า เหล่าชาวนาวีเผ่าขี้เถ้าที่ป่าเถื่อนและกระหายในอำนาจ นำโดยหัวหน้าเผ่าผู้เหี้ยมโหดที่มีชื่อว่า วารัง ครอบครัวของ เจค จะต้องต่อสู้เพื่อเอาชีวิตรอดและเพื่ออนาคตของดาวแพนโดร่า ในความขัดแย้งที่จะผลักดันทุกคนไปยังขีดสุดของทั้งร่างกายและจิตใจ"
                )
            )
        )
    }
}