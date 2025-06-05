package com.example.chitchat.presentation.screens.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.chitchat.presentation.viewmodel.MainViewModel
import com.example.chitchat.presentation.viewmodel.SearchViewModel


@Composable
fun SearchScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    searchViewModel: SearchViewModel
) {
    // screen size
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidthDp = configuration.screenWidthDp.dp

    // icon size for different screen sizes
    val iconSize = 30.dp
    // text size for different screen sizes
    val mainHeading = 30.sp
    val headingSize = 24.sp
    val contentSize = 18.sp


    // content
    val searchedNumber = searchViewModel.searchedNumber

    // color of the box
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color(0xFFEE8259))
    ) {

    }
}
