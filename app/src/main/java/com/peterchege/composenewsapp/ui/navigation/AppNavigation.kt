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
package com.peterchege.composenewsapp.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.peterchege.composenewsapp.core.util.Constants
import com.peterchege.composenewsapp.ui.screens.all_news.AllNewsScreen
import com.peterchege.composenewsapp.ui.screens.saved_news.SavedNewsScreen
import com.peterchege.composenewsapp.ui.screens.search_news.SearchNewsScreen
import com.peterchege.composenewsapp.ui.screens.single_news.SingleNewsScreen
import com.peterchege.composenewsapp.core.util.Screens

@Composable
fun AppNavigation(
    navController: NavHostController
){
    NavHost(
        navController =navController,
        startDestination = Screens.DASHBOARD_SCREEN,
    ){
        composable(Screens.DASHBOARD_SCREEN){
            DashBoardScreen(navHostController = navController)
        }
        composable(
            arguments = listOf(
                navArgument(name = "articleId") { type = NavType.IntType },
                navArgument(name ="source"){ type = NavType.StringType }
            ),
            route = Screens.SINGLE_NEWS_SCREEN + "/{articleId}/{source}"
        ){
            SingleNewsScreen()
        }
    }

}


@Composable
fun DashBoardScreen(
    navHostController: NavHostController
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                items = listOf(
                    BottomNavItem(
                        name="Home",
                        route = Screens.ALL_NEWS_SCREEN  ,
                        icon = Icons.Default.Home
                    ),

                    BottomNavItem(
                        name="Add",
                        route = Screens.SEARCH_NEWS_SCREEN   ,
                        icon = Icons.Default.Search
                    ),
                    BottomNavItem(
                        name="Wishlist",
                        route = Screens.SAVED_NEWS_SCREEN   ,
                        icon = Icons.Default.BookmarkBorder
                    ),

                ),
                navController = navController,
                onItemClick ={
                    navController.navigate(it.route)
                }
            )
        }
    ) { innerPadding ->
        // Apply the padding globally to the whole BottomNavScreensController
        Box(modifier = Modifier.padding(innerPadding)) {
            DashboardNavigation(navController = navController, navHostController = navHostController)
        }

    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomNavBar(
    items:List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick:(BottomNavItem) -> Unit
){
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.DarkGray,
        elevation = 5.dp
    ) {
        items.forEach{ item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected =selected ,
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Gray,
                onClick = { onItemClick(item) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (item.badgeCount > 0){

                            Icon(
                                imageVector = item.icon,
                                contentDescription =item.name
                            )

                        }else{
                            Icon(
                                imageVector = item.icon,
                                contentDescription =item.name
                            )
                        }
                        if (selected){
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp

                            )
                        }

                    }

                }
            )
        }


    }


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardNavigation(
    navController: NavHostController,
    navHostController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screens.ALL_NEWS_SCREEN ){

        composable(
            route = Screens.ALL_NEWS_SCREEN
        ){
            AllNewsScreen(
                navigateToSingleNewScreen = {
                    navHostController.navigate(route = Screens.SINGLE_NEWS_SCREEN + "/${it}/${Constants.ALL_NEW}")
                }
            )
        }
        composable(
            route = Screens.SEARCH_NEWS_SCREEN
        ){
            SearchNewsScreen(
                navigateToSingleNewsScreen = {
                    navHostController.navigate(route = Screens.SINGLE_NEWS_SCREEN + "/${it}/${Constants.SEARCH}")
                }
            )
        }
        composable(
            route = Screens.SAVED_NEWS_SCREEN
        ){
            SavedNewsScreen(
                navigateToSingleNewsScreen = {
                    navHostController.navigate(route = Screens.SINGLE_NEWS_SCREEN + "/${it}//${Constants.SAVED}")
                }
            )
        }
    }

}
data class BottomNavItem(
    val name:String,
    val route:String,
    val icon: ImageVector,
    val badgeCount:Int = 0,

    )