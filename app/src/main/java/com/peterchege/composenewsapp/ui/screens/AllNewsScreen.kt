package com.peterchege.composenewsapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.peterchege.composenewsapp.ui.components.NewsCard
import com.peterchege.composenewsapp.ui.viewmodels.AllNewsScreenViewModel
import com.peterchege.composenewsapp.util.Screens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AllNewsScreen(
    navController: NavController,
    viewModel: AllNewsScreenViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        if (viewModel.isLoading.value) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    color = Color.Blue,
                    modifier = Modifier.align(Alignment.Center)
                )

            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp)
            ){
                items(viewModel.articles.value){
                    NewsCard(
                        article = it,
                        onItemClick ={

                        } )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }


    }

}