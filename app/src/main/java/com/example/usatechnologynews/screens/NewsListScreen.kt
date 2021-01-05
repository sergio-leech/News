package com.example.usatechnologynews.screens

import AnimatedListTypeButton
import LazyGrid
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.example.usatechnologynews.model.Article
import com.example.usatechnologynews.model.ListType
import com.example.usatechnologynews.view_model.NewsViewModel
import dev.chrisbanes.accompanist.glide.GlideImage

@Composable
fun NewsListScreen(navHostController: NavHostController, newsViewModel: NewsViewModel) {

    val newsList = newsViewModel.newsList.observeAsState()
    newsList.value?.let { news ->
        NewsListContent(
            items = news,
            navHostController = navHostController,
            viewModel = newsViewModel
        )
    }
}

@Composable
fun NewsListContent(
    items: List<Article>,
    navHostController: NavHostController,
    viewModel: NewsViewModel
) {
    val listItemType = viewModel.newsListType.observeAsState(ListType.NONE)

    Scaffold(topBar = {
        TopAppBar(backgroundColor = MaterialTheme.colors.background) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (screenTitle, itemSortingType) = createRefs()
                Text(
                    text = "News",
                    color = MaterialTheme.colors.surface,
                    modifier = Modifier.constrainAs(screenTitle) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, 12.dp)
                    }
                )
                AnimatedListTypeButton(
                    listTypeState = listItemType.value,
                    onListTypeChanged = { listItemType ->
                        viewModel.setEpisodesListType(listItemType)
                    },
                    modifier = Modifier.constrainAs(itemSortingType) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end, 24.dp)
                    }
                )
            }

        }
    }) {
        Surface(color = MaterialTheme.colors.background) {
            when (listItemType.value) {
                ListType.GRID -> NewsGridList(items = items, navHostController = navHostController)
                ListType.LINEAR -> NewsLinearList(
                    items = items,
                    navHostController = navHostController
                )
                ListType.NONE -> NewsGridList(items = items, navHostController = navHostController)
            }
        }
    }
}

@Composable
fun NewsLinearList(items: List<Article>, navHostController: NavHostController) {
    LazyColumn {
        items(items = items) { news ->
            EpisodeListItem(navHostController = navHostController, article = news)
        }
    }
}

@Composable
fun EpisodeListItem(
    navHostController: NavHostController,
    article: Article
) {
    Card(
        backgroundColor = Color.White,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable(onClick = { navHostController.navigate("newsDetail/${article.url}") })
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            GlideImage(
                data = article.urlToImage ?: "",
                fadeIn = true,
                contentScale = ContentScale.FillBounds,
                loading = {
                    Box(Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            color = Color.Yellow,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                modifier = Modifier.height(90.dp).width(120.dp)
            )
            Text(
                text = article.title ?: "",
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun NewsGridList(items: List<Article>, navHostController: NavHostController) {
    LazyGrid(items = items, column = 2, hPadding = 8) { news ->
        NewsCard(article = news, navHostController = navHostController)

    }
}

@Composable
fun NewsCard(navHostController: NavHostController, article: Article) {
    Card(
        backgroundColor = Color.White,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp).fillMaxSize()
            .clickable(onClick = { navHostController.navigate("newsDetail/${article.url}") })
    ) {
        Log.d("TAG", "ID ${article.url}")
        ConstraintLayout {
            val (imageRef, titleRef) = createRefs()
            GlideImage(
                data = article.urlToImage ?: "",
                fadeIn = true,
                contentScale = ContentScale.FillBounds,
                loading = {
                    Box(Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colors.surface,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }, modifier = Modifier.height(120.dp).constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
            Text(
                text = article.title ?: "",
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp).constrainAs(titleRef) {
                    top.linkTo(imageRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

        }
    }
}
