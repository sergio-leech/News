package com.example.usatechnologynews

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.usatechnologynews.screens.NewsDetailScreen
import com.example.usatechnologynews.screens.NewsListScreen
import com.example.usatechnologynews.screens.SplashScreen
import com.example.usatechnologynews.ui.USATechnologyNewsTheme
import com.example.usatechnologynews.view_model.NewsViewModel

@Composable
fun NewsApp(newsViewModel: NewsViewModel){
    USATechnologyNewsTheme {
        Surface(color = MaterialTheme.colors.background) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "splash"){
                composable("splash"){
                    SplashScreen(navHostController = navController)
                }
                composable("newsList"){
                   NewsListScreen(navHostController = navController, newsViewModel =newsViewModel )
                }
                composable(route = "newsDetail/{url}",
                    arguments =listOf(navArgument("url") { type = NavType.StringType })){navBackStackEntry ->
                    navBackStackEntry.arguments?.getString("url")?.let {id->
                        NewsDetailScreen(navHostController = navController, viewModel = newsViewModel, newsId =id)
                    }

                }
            }
        }
    }
}