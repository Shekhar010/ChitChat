package com.example.chitchat.presentation.screens.supports

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.chitchat.R
import com.example.chitchat.model.navigation.Chat
import com.example.chitchat.model.navigation.Login
import com.example.chitchat.model.navigation.Register
import com.example.chitchat.model.user.UserRes
import com.example.chitchat.presentation.formatMillisToDate
import com.example.chitchat.presentation.viewmodel.AuthViewModel

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Text("Home Screen")
}


// user information
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DisplayInformation(
    authViewModel: AuthViewModel, navController: NavHostController, userInfo: UserRes? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
    ) {
        Text(
            "Information", style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        // email of the user
        Row(
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.email_icon),
                contentDescription = "Email",
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 10.dp),
                tint = Color.Unspecified
            )
            Text(
                "E-mail :", color = Color.DarkGray
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = userInfo?.email ?: "N/A", style = TextStyle(
                    fontSize = 16.sp, fontWeight = FontWeight.SemiBold
                )
            )
        }

        // phone number of the user
        Row(
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.phone_icon),
                contentDescription = "Phone",
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 10.dp),
                tint = Color.DarkGray
            )
            Text(
                "Phone Number : ", color = Color.DarkGray
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "+91 | " + userInfo?.phone, style = TextStyle(
                    fontSize = 16.sp, fontWeight = FontWeight.SemiBold
                )
            )
        }

        // joined date of user
        Row(
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.calender_icon),
                contentDescription = "calender",
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 10.dp),
                tint = Color.DarkGray
            )
            Text(
                "Joined Date : ", color = Color.DarkGray
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(userInfo?.accountCreatedAt?.let { formatMillisToDate(it) } ?: "N/A",
                style = TextStyle(
                    fontSize = 16.sp, fontWeight = FontWeight.SemiBold
                ))
        }

        Row(

        ) {
            Button(
                onClick = {
                    // log-out
                    authViewModel.logout()
                    navController.navigate(Login) {
                        popUpTo(0)
                    }
                },
                border = BorderStroke(1.dp, Color.DarkGray),
                colors = ButtonDefaults.buttonColors(
                    Color.Transparent
                )
            ) {
                Text(
                    "Log-out", style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OnboardingScreen(
    navController: NavHostController, authViewModel: AuthViewModel = hiltViewModel()
) {

    // observe if user is logged in or not
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    if (isLoggedIn) {
        // navigate to chat screen
        navController.navigate(Chat) {
            popUpTo(Login) {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // background image
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Infinitely Secure chats\nbetween you,\n" + " your friends and loved\nones\n",
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .padding(bottom = 20.dp),
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                )
            )

            Text(
                buildAnnotatedString {
                    append("Your conversations are shielded by top-notch ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold, color = Color.DarkGray
                        )
                    ) {
                        append("encryption")
                    }
                    append(
                        ", ensuring that only you and your chosen recipients have access.\n" + "\n" + "Feel at ease sharing messages and files, knowing that your "
                    )
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold, color = Color.DarkGray
                        )
                    ) {
                        append("privacy")
                    }
                    append(" is our priority.")
                }, modifier = Modifier.fillMaxWidth(.8f), style = TextStyle(
                    textAlign = TextAlign.Center, fontSize = 16.sp, fontWeight = FontWeight.Light
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    // navigate to Register screen
                    navController.navigate(Register) {
                        popUpTo(Register)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .height(60.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    Color.DarkGray
                ),
                shape = RoundedCornerShape(20.dp)

            ) {
                Text(
                    "Create an account", modifier = Modifier
                )
            }
        }
    }
}

