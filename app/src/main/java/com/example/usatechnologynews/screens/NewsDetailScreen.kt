package com.example.usatechnologynews.screens

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.tapGestureFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientUriHandler
import androidx.compose.ui.platform.UriHandlerAmbient
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.usatechnologynews.model.Article
import com.example.usatechnologynews.view_model.NewsViewModel
import dev.chrisbanes.accompanist.glide.GlideImage

@Composable
fun NewsDetailScreen(
    navHostController: NavHostController,
    viewModel: NewsViewModel,
    newsId: String
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Back to others",
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack)
                    }
                },
                backgroundColor = Color.Black
            )
        },
        bodyContent = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            val targetNews = viewModel.getNewsById(newsId)
            targetNews?.let { NewsContent(it, modifier) }
        }
    )
}

@Composable
fun NewsContent(news: Article, modifier: Modifier) {
    ScrollableColumn(modifier = modifier.background(Color.Black).fillMaxSize()) {
        NewsImage(url = news.urlToImage ?: "")
        Spacer(modifier = Modifier.preferredHeight(16.dp))
        NewsInfo(news = news)

    }

}

@Composable
fun NewsImage(url: String) {
    Card(
        shape = RoundedCornerShape(bottomLeft = 32.dp, bottomRight = 32.dp),
        backgroundColor = Color.White,
        modifier = Modifier.fillMaxWidth().height(240.dp)
    ) {
        GlideImage(
            data = url,
            fadeIn = true,
            contentScale = ContentScale.FillBounds,
            loading = {
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.surface,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun NewsInfo(news: Article) {
    Card(
        shape = RoundedCornerShape(size = 32.dp),
        backgroundColor = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            NewsNameTitle(newsTitle = news.title ?: "")
            Divider(color = Color.Black)
            Spacer(modifier = Modifier.preferredHeight(8.dp))
            Text(
                text = news.description ?: "",
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
            Spacer(modifier = Modifier.preferredHeight(7.dp))
            NewsUrl(url = news.url ?: "")
            Spacer(modifier = Modifier.preferredHeight(8.dp))
        }
    }
}

@Composable
fun NewsNameTitle(newsTitle: String) {
    Text(
        text = newsTitle,
        style = TextStyle(fontWeight = FontWeight.Bold),
        color = Color.Black,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 32.dp).fillMaxWidth()
    )
}

@Composable
fun NewsUrl(url: String) {
    val uriHandler = AmbientUriHandler.current

    val layoutResult = remember {
        mutableStateOf<TextLayoutResult?>(null)
    }

    val text = "Link to the news"
    val annotatedString = buildAnnotatedString {
        pushStyle(
            style = SpanStyle(
                color = Color.Yellow,
                textDecoration = TextDecoration.Underline
            )
        )
        append(text)
        addStringAnnotation(
            tag = "URL",
            annotation = url,
            start = 0,
            end = text.length
        )
    }
    Text(
        fontSize = 16.sp,
        text = annotatedString,
        modifier = Modifier.background(Color.Black).padding(10.dp)
            .tapGestureFilter { offsetPosition ->
                layoutResult.value?.let {
                    val position = it.getOffsetForPosition(offsetPosition)
                    annotatedString.getStringAnnotations(position, position).firstOrNull()
                        ?.let { result ->
                            if (result.tag == "URL") {
                                uriHandler.openUri(result.item)
                            }
                        }
                }
            },
        onTextLayout = { layoutResult.value = it }
    )
}