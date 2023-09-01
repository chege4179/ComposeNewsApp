package com.peterchege.composenewsapp.ui.screens.all_news

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.peterchege.composenewsapp.core.api.responses.NetworkArticle
import com.peterchege.composenewsapp.domain.models.ArticleUI
import com.peterchege.composenewsapp.ui.components.NewsCard

@Composable
fun AllNewsScreen(
    navigateToSingleNewScreen:(Int) -> Unit,
    viewModel: AllNewsScreenViewModel = hiltViewModel()
) {
    val articlesPagingItems = viewModel.articlesPagingDataFlow.collectAsLazyPagingItems()

    AllNewsScreenContent(
        articlesPagingItems = articlesPagingItems,
        navigateToSingleNewScreen = navigateToSingleNewScreen
    )


}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AllNewsScreenContent(
    navigateToSingleNewScreen:(Int) -> Unit,
    articlesPagingItems: LazyPagingItems<ArticleUI>,
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = scaffoldState) {
        if (articlesPagingItems.loadState.refresh is LoadState.Error) {
            scaffoldState.snackbarHostState.showSnackbar(message = "Failed to fetch news")
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
                        articleUI = article,
                        onItemClick = {
                            it.id?.let { it1 ->
                                navigateToSingleNewScreen(it1)
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                if (articlesPagingItems.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
            }
        }
    }

}