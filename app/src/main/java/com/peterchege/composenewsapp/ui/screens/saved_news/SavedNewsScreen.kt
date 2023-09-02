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
package com.peterchege.composenewsapp.ui.screens.saved_news

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.composenewsapp.ui.components.ErrorComponent
import com.peterchege.composenewsapp.ui.components.LoadingComponent
import com.peterchege.composenewsapp.ui.components.NewsCard

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SavedNewsScreen(
    navigateToSingleNewsScreen:(Int) -> Unit,
    viewModel: SavedNewsScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    SavedNewsScreenContent(
        uiState = uiState,
        navigateToSingleNewsScreen = navigateToSingleNewsScreen,
        unBookmarkArticle = viewModel::unBookmarkArticle,
    )



}
@Composable
fun SavedNewsScreenContent(
    uiState:SavedNewsScreenUiState,
    unBookmarkArticle:(Int) -> Unit,
    navigateToSingleNewsScreen:(Int) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(text = "Saved News Articles")
            })
        }
    ) { paddingValues ->
        when(uiState){
            is SavedNewsScreenUiState.Loading -> {
                LoadingComponent()
            }
            is SavedNewsScreenUiState.Error -> {
                ErrorComponent(retryCallback = { /*TODO*/ }, message = "Failed to load bookmarked Articles")
            }
            is SavedNewsScreenUiState.Success -> {
                val articles = uiState.articles
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(10.dp)
                    ,
                ){
                    items(items = articles){article ->
                        NewsCard(
                            articleUI = article,
                            onItemClick = { article.id?.let { it1 -> navigateToSingleNewsScreen(it1) } },
                            onSavedArticle = {  },
                            onUnbookmarkArticle = { article.id?.let { it1 -> unBookmarkArticle(it1) } },
                            isBookmarked = true
                        )
                    }

                }
            }
        }
    }

}