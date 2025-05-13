package com.example.chitchat.presentation.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import coil.compose.AsyncImage
import com.example.chitchat.R
import com.example.chitchat.model.navigation.Chat
import com.example.chitchat.model.navigation.Login
import com.example.chitchat.model.navigation.Register
import com.example.chitchat.presentation.viewmodel.AuthViewModel
import com.example.chitchat.presentation.viewmodel.MainViewModel

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Text("Home Screen")
}

// profile of user
@Composable
fun ProfileScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController
) {
    // observe the state of the user
    val userInfo = mainViewModel.userInformation

    // change the color of the notification bar
    val context = LocalContext.current

    // Column
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // profile picture
        if (userInfo?.profileImageUrl != null) {
            // use Async image
            AsyncImage(
                model = userInfo.profileImageUrl,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(30.dp))
            )
        } else {
            Image(
                painter = painterResource(R.drawable.icons8_test_account_96),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        // name of the user
        Text(
            text = userInfo?.fullName ?: "Shekhar",
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        // spacer
        Spacer(modifier = Modifier.height(10.dp))
        // joined date and Username
        Row() {
            // text for username
            Text(
                "shekhar_yadav_2002",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier
                    .padding(end = 10.dp)
            )
            // separator
            VerticalDivider(modifier = Modifier.height(20.dp))

            // text for joined date
            Text(
                "Joined : May 2025",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier
                    .padding(start = 10.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        // buttons
        Row(
            modifier = Modifier
                .fillMaxWidth(.8f),
            horizontalArrangement = Arrangement.Center
        ) {
            // button for edit profile
            Button(
                onClick = { },
                modifier = Modifier
                    .weight(.5f),
                colors = ButtonDefaults.buttonColors(
                    Color.Transparent
                ),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    "Edit Profile",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            // button for more
            Button(
                onClick = { },
                modifier = Modifier
                    .weight(.5f),
                colors = ButtonDefaults.buttonColors(
                    Color.Transparent
                ),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Icon(
                    painter = painterResource(R.drawable.three_dots),
                    contentDescription = "Send",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    "More",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }
        // user bio
        Text(
            "I'm a passionate Android Developer with a strong focus on building scalable, user-friendly mobile applications using Kotlin, Jetpack Compose, MVVM architecture, and modern Android development tools. I specialize in implementing clean architecture, integrating APIs, managing local data with Room, and ensuring smooth user experiences through thoughtful UI/UX. With a solid grasp of Firebase, Hilt, and third-party SDKs.",
            style = TextStyle(
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth(.7f)
                .padding(top = 20.dp, bottom = 10.dp)
        )


        // user information
        DisplayInformation(authViewModel, navController = navController)

    }
}

// user information
@Composable
fun DisplayInformation(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
    ) {
        Text(
            "Information",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        // email of the user
        Row(
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp),
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
                "E-mail :",
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "shekharyadav1002@gmail.com",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        // phone number of the user
        Row(
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.phone_icon),
                contentDescription = "Email",
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 10.dp),
                tint = Color.DarkGray
            )
            Text(
                "Phone Number : ",
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "+91 | " + "7037534525",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        // joined date of user
        Row(
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.calender_icon),
                contentDescription = "Email",
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 10.dp),
                tint = Color.DarkGray
            )
            Text(
                "Joined Date : ",
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "April 2025", style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
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
                    "Log-out",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }
    }
}


@Composable
fun OnboardingScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
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
        modifier = Modifier
            .fillMaxSize()
    ) {
        // background image
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
        )
//        Image(
//            painter = painterResource(R.drawable.subtract),
//            contentDescription = "logo",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .fillMaxWidth()
//        )


        // navigate to

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
                "Infinitely Secure chats\nbetween you,\n" +
                        " your friends and loved\nones\n",
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
                            fontWeight = FontWeight.SemiBold,
                            color = Color.DarkGray
                        )
                    ) {
                        append("encryption")
                    }
                    append(
                        ", ensuring that only you and your chosen recipients have access.\n"
                                + "\n"
                                + "Feel at ease sharing messages and files, knowing that your "
                    )
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            color = Color.DarkGray
                        )
                    ){
                        append("privacy")
                    }
                    append(" is our priority.")
                },
                modifier = Modifier
                    .fillMaxWidth(.8f),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
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
                    "Create an account",
                    modifier = Modifier
                )
            }
        }


    }
}
