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
package com.peterchege.composenewsapp.ui.screens.search_news

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.composenewsapp.core.api.responses.NetworkArticle
import com.peterchege.composenewsapp.domain.mappers.toCacheEntity
import com.peterchege.composenewsapp.domain.mappers.toPresentationModel
import com.peterchege.composenewsapp.domain.models.ArticleUI
import com.peterchege.composenewsapp.ui.components.ErrorComponent
import com.peterchege.composenewsapp.ui.components.LoadingComponent
import com.peterchege.composenewsapp.ui.components.NewsCard


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchNewsScreen(
    navigateToSingleNewsScreen: (Int) -> Unit,
    viewModel: SearchNewsScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val query by viewModel.searchTerm.collectAsStateWithLifecycle()
    val searchArticles by viewModel.searchArticles.collectAsStateWithLifecycle()

    SearchNewsScreenContent(
        navigateToSingleNewsScreen = navigateToSingleNewsScreen,
        uiState = uiState,
        onChangeQuery = viewModel::onChangeQuery,
        query = query,
        searchArticles = searchArticles
    )

}

@Composable
fun SearchNewsScreenContent(
    navigateToSingleNewsScreen: (Int) -> Unit,
    uiState: SearchNewsScreenUiState,
    onChangeQuery: (String) -> Unit,
    query: String,
    searchArticles:List<ArticleUI>
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = query,
                onValueChange = { onChangeQuery(it) },
                placeholder = {
                    Text(text = "Search News......")
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onChangeQuery(query)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search News",
                            modifier = Modifier.size(30.dp),
                        )
                    }
                }
            )
            when (uiState) {
                is SearchNewsScreenUiState.Idle -> {
                    ErrorComponent(
                        retryCallback = { },
                        message = "Start searching"
                    )
                }

                is SearchNewsScreenUiState.Searching -> {
                    LoadingComponent()
                }

                is SearchNewsScreenUiState.Error -> {
                    ErrorComponent(
                        retryCallback = { },
                        message = uiState.message
                    )
                }

                is SearchNewsScreenUiState.ResultsFound -> {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(items = searchArticles){ article ->
                            NewsCard(
                                isBookmarked = false,
                                articleUI = article,
                                onItemClick = {
                                    it.id?.let { it1 ->
                                        navigateToSingleNewsScreen(it1)
                                    }
                                },
                                onSavedArticle = {  },
                                onUnbookmarkArticle = {  }
                            )
                        }

                    }

                }
            }
        }


    }
}