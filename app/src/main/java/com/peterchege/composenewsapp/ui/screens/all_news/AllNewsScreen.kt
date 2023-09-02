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
package com.peterchege.composenewsapp.ui.screens.all_news

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.peterchege.composenewsapp.core.api.responses.NetworkArticle
import com.peterchege.composenewsapp.core.util.UiEvent
import com.peterchege.composenewsapp.domain.models.ArticleUI
import com.peterchege.composenewsapp.ui.components.ErrorComponent
import com.peterchege.composenewsapp.ui.components.NewsCard
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AllNewsScreen(
    navigateToSingleNewScreen:(Int) -> Unit,
    viewModel: AllNewsScreenViewModel = hiltViewModel()
) {
    val articlesPagingItems = viewModel.articlesPagingDataFlow.collectAsLazyPagingItems()
    val bookmarkedArticles by viewModel.bookmarkedArticles.collectAsStateWithLifecycle()

    AllNewsScreenContent(
        articlesPagingItems = articlesPagingItems,
        navigateToSingleNewScreen = navigateToSingleNewScreen,
        bookmarkedArticles = bookmarkedArticles,
        onBookmarkArticle  = viewModel::bookmarkedArticle,
        onUnbookmarkArticle = viewModel::unBookmarkArticle,
        eventFlow = viewModel.eventFlow,
    )


}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AllNewsScreenContent(
    navigateToSingleNewScreen:(Int) -> Unit,
    bookmarkedArticles:List<ArticleUI>,
    articlesPagingItems: LazyPagingItems<ArticleUI>,
    onBookmarkArticle:(ArticleUI) -> Unit,
    onUnbookmarkArticle:(Int) -> Unit,
    eventFlow:SharedFlow<UiEvent>,
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = scaffoldState) {
        if (articlesPagingItems.loadState.refresh is LoadState.Error) {
            scaffoldState.snackbarHostState.showSnackbar(message = "Failed to fetch news")
        }

    }
    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar {
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Compose News App",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,

                    )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp)
        ) {

            items(
                count = articlesPagingItems.itemCount,
            ) { index ->
                val article = articlesPagingItems[index]
                if (article != null) {
                    NewsCard(
                        isBookmarked = bookmarkedArticles.map { it.description }.contains(article.description),
                        articleUI = article,
                        onItemClick = {
                            it.id?.let { it1 ->
                                navigateToSingleNewScreen(it1)
                            }
                        },
                        onSavedArticle =  onBookmarkArticle,
                        onUnbookmarkArticle = onUnbookmarkArticle
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                if (articlesPagingItems.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
            }
            item{
                if(articlesPagingItems.loadState.append is LoadState.Error){
                    ErrorComponent(
                        retryCallback = { articlesPagingItems.retry() },
                        message = "Failed to load more results"
                    )
                }
            }
        }
    }

}