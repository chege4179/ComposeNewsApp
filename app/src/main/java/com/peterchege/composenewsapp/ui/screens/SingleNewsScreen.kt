package com.peterchege.composenewsapp.ui.screens

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.peterchege.composenewsapp.models.Article

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SingleNewsScreen(
    navController: NavController,
    article: Article
) {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(factory = {
            WebView(context).apply {
                webViewClient = WebViewClient()

                loadUrl(article.url)
            }
        })

    }

}