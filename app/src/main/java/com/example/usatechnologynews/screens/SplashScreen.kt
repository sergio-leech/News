package com.example.usatechnologynews.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navHostController: NavHostController
) {
    SplashContent()
    LaunchedEffect(subject = Dispatchers.IO) {
        delay(500)
       navHostController.navigate("newsList")
    }
}

@Composable
fun SplashContent() {
    ConstraintLayout(
        modifier = Modifier.background(color = MaterialTheme.colors.background).fillMaxSize()
    ) {
      val (appLogoRef,appLoadingRef) = createRefs()
        Text(
            text = "USA NEWS",
            fontSize = 40.sp,
            color = MaterialTheme.colors.surface,
            modifier = Modifier.constrainAs(appLogoRef) {
               linkTo(
                   bottom = parent.bottom,
                   top = parent.top,
                   start = parent.start,
                   end = parent.end
               )
            })

            CircularProgressIndicator(
                color = MaterialTheme.colors.surface,
                strokeWidth = 4.dp,
                modifier = Modifier.constrainAs(appLoadingRef){
                    top.linkTo(appLogoRef.bottom,40.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
    }
}
