/*
 * Copyright 2023 Compose News App By Peter Chege
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peterchege.composenewsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.peterchege.composenewsapp.domain.models.ArticleUI

@Composable
fun NewsCard(
    articleUI: ArticleUI,
    onItemClick:(articleUI: ArticleUI) -> Unit,
    onSavedArticle:(ArticleUI) ->Unit,
    onUnbookmarkArticle:(Int) -> Unit,
    isBookmarked:Boolean,

) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(15.dp), clip = true)
            .clickable { onItemClick(articleUI) },
        backgroundColor = Color.White,
        shape = RoundedCornerShape(15.dp),

        ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)) {
            articleUI.let {
                SubcomposeAsyncImage(
                    model = articleUI.urlToImage,
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
                            text = articleUI?.title ?: "",
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
                            )
                            Text(
                                text = articleUI.source?.name ?:"",
                                modifier = Modifier
                                    .padding(vertical = 7.dp)
                                    ,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp

                            )
                            Text(
                                text = "  " + articleUI.publishedAt,
                                modifier = Modifier.padding(vertical = 7.dp)
                            )
                            IconButton(onClick = {
                                if (isBookmarked){
                                    articleUI.id?.let { it1 -> onUnbookmarkArticle(it1) }
                                }else{
                                    onSavedArticle(articleUI)
                                }

                            }) {
                                Icon(
                                    imageVector = if (isBookmarked)
                                        Icons.Default.Bookmark
                                    else
                                        Icons.Default.BookmarkBorder,
                                    contentDescription = "Save Article",
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}