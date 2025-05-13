package com.example.chitchat.presentation.screens.auth

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.chitchat.R
import com.example.chitchat.model.navigation.Login
import com.example.chitchat.presentation.viewmodel.AuthViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController,
) {

    val userRegistered by authViewModel.isRegistered.collectAsState()
//
//    LaunchedEffect(userRegistered) {
//        navController.navigate(Login)
//    }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.login_background),
            contentDescription = "logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                    text = "Welcome User !",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFEE8259)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Please fill the details to continue, and if you are a member please click on Log-in",
                    fontSize = 15.sp
                )
            }

            var name by rememberSaveable { mutableStateOf("") }
            var phone by rememberSaveable { mutableStateOf("") }
            var email by rememberSaveable { mutableStateOf("") }
            var password by rememberSaveable { mutableStateOf("") }

            Spacer(modifier = Modifier.height(20.dp))

            /* UI for -> name field */
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                enabled = true
            )
            /* UI for -> email field */
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("phone") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                enabled = true
            )
            /* UI for -> email field */
            Spacer(modifier = Modifier.height(10.dp))
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
                        // register the user
                        // call the function to register the user and navigate to login page
                        authViewModel.register(email, password, phone, name)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFFEE8259)
                    )
                ) {
                    Text("Register-User")

                }
            }
            // observe the Register status

            /* UI for -> ask the user to register */
            Spacer(modifier = Modifier.height(30.dp))


            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(modifier = Modifier.width(50.dp))
                    Text(
                        "Already have a Account",
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp),
                        style = TextStyle(
                            fontSize = 12.sp,
                        )
                    )
                    HorizontalDivider(modifier = Modifier.width(50.dp))
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 20.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    onClick = {
                        // navigate the user to login screen
                        navController.navigate(Login)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    border = BorderStroke(1.dp, Color(0xFFEE8259))
                ) {
                    Text(
                        "Log-in",
                        style = TextStyle(
                            color = Color(0xFFEE8259)
                        )
                    )
                }
            }
        }
    }
}
