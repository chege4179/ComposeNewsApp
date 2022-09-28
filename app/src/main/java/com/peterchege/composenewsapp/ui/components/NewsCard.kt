package com.peterchege.composenewsapp.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberImagePainter
import com.peterchege.composenewsapp.models.Article

@Composable
fun NewsCard(
    article: Article,
    onItemClick:(article:Article) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 2.dp,shape = RoundedCornerShape(15.dp), clip= true)
            .clickable { onItemClick(article) },
        backgroundColor = Color.White,
        shape = RoundedCornerShape(15.dp),

        ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)) {
            article.let {
                SubcomposeAsyncImage(
                    model = article.urlToImage,
                    loading = {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    },
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .height(150.dp),
                    contentDescription = "Post Image"
                )


                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()

                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = article.title,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold,


                            )
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "By : ",
                                modifier = Modifier
                                    .padding(vertical = 7.dp)
//                                    .clickable {
//                                        onProfileNavigate(post.postAuthor)
//                                    }
                            )
                            Text(
                                text = article.source.name,
                                modifier = Modifier
                                    .padding(vertical = 7.dp)
                                    ,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp

                            )
                            Text(
                                text = article.publishedAt,
                                modifier = Modifier.padding(vertical = 7.dp)
                            )

                        }


                    }

                }
            }
        }
    }

}