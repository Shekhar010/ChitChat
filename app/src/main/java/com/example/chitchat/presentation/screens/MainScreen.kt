package com.example.chitchat.presentation.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.chitchat.R
import com.example.chitchat.model.navigation.Chat
import com.example.chitchat.model.navigation.Login
import com.example.chitchat.model.navigation.Onboarding
import com.example.chitchat.model.navigation.Profile
import com.example.chitchat.model.navigation.Register
import com.example.chitchat.presentation.screens.auth.LoginScreen
import com.example.chitchat.presentation.screens.auth.RegisterScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    // navigation
    val navController = rememberNavController()
    // current entry in backstack
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Log.d("current route", "$currentRoute")
    Log.d("current user", "${Firebase.auth.currentUser}")

    // scaffold for navigation bar
    Scaffold(
        // top app bar
        topBar = {
            if (currentRoute == "com.example.chitchat.model.navigation.Chat")
                TopAppBar(
                    title = {
                        // chats text
                        Text(
                            "Chats",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    actions = {
                        // another icon for search
                        IconButton(
                            onClick = {
                                // navigate to profile section of the user

                            }
                        ) {
                            // use this icon when the user has no profile picture
                            Icon(
                                painter = painterResource(R.drawable.search_icon),
                                contentDescription = "profile icon",
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }

                        // icon button for something like profile
                        IconButton(
                            onClick = {
                                // open a drawer
                            }
                        ) {
                            // use this icon when the user has no profile picture
                            Icon(
                                painter = painterResource(R.drawable.three_dots_vertical),
                                contentDescription = "profile icon",
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                    }
                )
        },
        modifier = Modifier
            .fillMaxSize(),
        // bottom navigation bar
        bottomBar = {
            if(currentRoute != "com.example.chitchat.model.navigation.Onboarding" && currentRoute != "com.example.chitchat.model.navigation.Register" && currentRoute != "com.example.chitchat.model.navigation.Login")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .fillMaxWidth(.9f)
                        .background(
                            Color.DarkGray,
                            RoundedCornerShape(20.dp)
                        )
                        .height(80.dp)
                        .align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // icon button
                    IconButton(
                        onClick = {
                            // navigate to chat screen
                            if (currentRoute != "com.example.chitchat.model.navigation.Chat")
                                navController.navigate(Chat)
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.chat_icon),
                            contentDescription = "chat",
                            tint = MaterialTheme.colorScheme.background,
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }

                    // icon for group
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.group_icon),
                            contentDescription = "group",
                            tint = MaterialTheme.colorScheme.background,
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }

                    // icon button for profile
                    IconButton(
                        onClick = {
                            // navigate to the profile screen
                            if (currentRoute != "com.example.chitchat.model.navigation.Profile")
                                navController.navigate(Profile)
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.profile_icon),
                            contentDescription = "chat",
                            tint = MaterialTheme.colorScheme.background,
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }
                }
            }

        }

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Content(navController = navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Content(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Onboarding,
    ) {
        // navigate to chat screen
        composable<Chat> {
            ChatScreen(Modifier)
        }
        // onboarding screen
        composable<Onboarding> {
            OnboardingScreen(
                navController = navController
            )
        }

        // navigate to profile screen
        composable<Profile> {
            ProfileScreen(
                navController = navController
            )
        }

        // login screen
        composable<Login> {
            LoginScreen(
                navController = navController
            )
        }

        // register screen
        composable<Register> {
            RegisterScreen(
                navController = navController
            )
        }

    }
}