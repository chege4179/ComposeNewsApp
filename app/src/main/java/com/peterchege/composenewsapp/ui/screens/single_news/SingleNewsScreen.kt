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
package com.peterchege.composenewsapp.ui.screens.single_news

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.composenewsapp.ui.components.LoadingComponent


@Composable
fun SingleNewsScreen(
    viewModel: SingleNewsScreenViewModel = hiltViewModel(),
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SingleNewsScreenContent(uiState = uiState)
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SingleNewsScreenContent(
    uiState: SingleNewsScreenUiState,
) {
    when(uiState){
        is SingleNewsScreenUiState.Loading -> {
            LoadingComponent()
        }
        is SingleNewsScreenUiState.Error -> {
            Text(text = uiState.message)
        }
        is SingleNewsScreenUiState.Success -> {
            val article = uiState.article
            val context = LocalContext.current
            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) {
                AndroidView(factory = {
                    WebView(context).apply {
                        webViewClient = WebViewClient()

                        loadUrl(article.url ?:"")
                    }
                })

            }
        }

    }


}