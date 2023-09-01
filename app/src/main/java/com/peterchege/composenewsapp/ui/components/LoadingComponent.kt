package com.peterchege.composenewsapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingComponent() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = Color.Blue,
            modifier = Modifier.align(Alignment.Center)
        )

    }

}