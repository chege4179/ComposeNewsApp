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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.peterchege.composenewsapp.ui.screens.AllNewsScreen
import com.peterchege.composenewsapp.ui.screens.SavedNewsScreen
import com.peterchege.composenewsapp.ui.screens.SearchNewsScreen
import com.peterchege.composenewsapp.ui.screens.SingleNewsScreen
import com.peterchege.composenewsapp.ui.theme.AppNavigation
import com.peterchege.composenewsapp.util.Screens

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
        composable(Screens.SINGLE_NEWS_SCREEN + "/{id}"){
            SingleNewsScreen(navController = navController)
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
            AllNewsScreen(navController = navHostController)
        }
        composable(
            route = Screens.SEARCH_NEWS_SCREEN
        ){
            SearchNewsScreen(navController = navHostController)
        }
        composable(
            route = Screens.SAVED_NEWS_SCREEN
        ){
            SavedNewsScreen(navController = navHostController)
        }
    }

}
data class BottomNavItem(
    val name:String,
    val route:String,
    val icon: ImageVector,
    val badgeCount:Int = 0,

    )