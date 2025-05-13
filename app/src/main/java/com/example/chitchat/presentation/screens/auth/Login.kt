package com.example.chitchat.presentation.screens.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.chitchat.R
import com.example.chitchat.model.navigation.Chat
import com.example.chitchat.model.navigation.Login
import com.example.chitchat.model.navigation.Register
import com.example.chitchat.presentation.viewmodel.AuthViewModel

// ui for login screen
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController
) {

    // observe the login state
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    // context
    val context = LocalContext.current

    // observe the login status
    if (isLoggedIn) {
        // show toast
        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
        // navigate to chat Screen
        navController.navigate(Chat) {
            popUpTo(0) { inclusive = true }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Image(
            painter = painterResource(R.drawable.login_background),
            contentDescription = "logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
        )

        // ui for the login page
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 40.dp, 0.dp, 20.dp)
                .padding(end = 20.dp),
            horizontalAlignment = Alignment.Start,
        )
        {
            /* UI for -> title text and its description */
            Spacer(modifier = Modifier.height(40.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(.8F)
            ) {
                Text(
                    text = "ChitChat",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Welcome back !",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFEE8259)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Please fill the login details to continue, and if you are not a member plesae click on register now",
                    fontSize = 15.sp
                )
            }


            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            Spacer(modifier = Modifier.height(20.dp))

            /* UI for -> email field */
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                enabled = true
            )
            /* UI for -> password field */
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = PasswordVisualTransformation()
            )

            /* UI for -> login button */
            Spacer(modifier = Modifier.height(30.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                        // when button is clicked sign in the user
                        // 1. check if there exist user name and Password
                        if (email.isEmpty() || password.isEmpty()) {
                            // don't allow to login and toast a message to the user to fill the details
                            Toast.makeText(context, "Please fill the details", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            // allow to login
                            // use the auth viewmodel
                            authViewModel.login(email, password)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFFEE8259)
                    )
                ) {
                    Text("Log-in")

                }
            }


            /* UI for -> ask the user to register */
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                // text for registration
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    "Not a member? Register now",
                    modifier = Modifier
                        .clickable {
                            // navigate to register screen
                            navController.navigate(Register)
                        }
                )
            }
        }
    }


}
