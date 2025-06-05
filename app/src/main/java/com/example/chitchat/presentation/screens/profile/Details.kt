package com.example.chitchat.presentation.screens.profile

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chitchat.R
import com.example.chitchat.presentation.viewmodel.MainViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserDetails(
    mainViewModel: MainViewModel,
    navController: NavController,
) {
    // get the user details from the viewmodel
    LaunchedEffect (Unit){
        mainViewModel.getUserDetails()
    }
    val userDetails by mainViewModel.user.collectAsState()
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.left_arrow),
                    contentDescription = "back",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .clickable {
                            navController.navigateUp()
                        }
                        .align(Alignment.CenterStart)
                )
                Text(
                    text = "Your Details",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    ) { paddingValues ->
        val padding = paddingValues
        Column(
            modifier = Modifier
                .padding(top = 40.dp, start = paddingValues.calculateTopPadding(), end = 20.dp)
        ) {
            val context = LocalContext.current
            UserDetails(
                mainViewmodel = mainViewModel,
                context = context
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserDetails(
    mainViewmodel: MainViewModel,
    context: Context,
) {

    // user info
    val userInfo by mainViewmodel.user.collectAsState()
    LaunchedEffect(Unit) {
        mainViewmodel.getUserDetails()
    }
    userInfo?.userId?.let { Log.d("user details", it) }

    var newMobile by remember { mutableStateOf(userInfo?.phone) }
    var name by remember { mutableStateOf(userInfo?.fullName) }
    var email by remember { mutableStateOf(userInfo?.email) }
    var isEditable by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
            .border(1.dp, Color(0x60DEDEDE), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) Color(0X000fde00) else Color(
                0X000fde00
            )
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
        ) {
            Text(
                "Mobile Number",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(Alignment.CenterStart)
            )

            // button to edit the details
            Button(
                onClick = {
                    isEditable = !isEditable
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    "Change",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                )
            }
        }

        // user information

        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
        ) {
            newMobile?.let {
                OutlinedTextField(
                    value = it,
                    onValueChange = {
                        newMobile = it
                    }, // empty
                    readOnly = !isEditable,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxWidth()
    ) {
        // full name of the user
        Text(
            "Full Name",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier
                .padding(start = 10.dp, bottom = 10.dp)
        )
        name?.let {
            OutlinedTextField(
                value = it,
                onValueChange = {
                    name = it
                }, // empty
                readOnly = if (isEditable) false else true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                )
        }

        Spacer(modifier = Modifier.height(15.dp))
        // email of the user
        Text(
            "E-mail",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier
                .padding(start = 10.dp, bottom = 10.dp)
        )
        email?.let {
            OutlinedTextField(
                value = it,
                onValueChange = {
                    email = it
                }, // empty
                readOnly = !isEditable,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
            )
        }


        Spacer(modifier = Modifier.height(15.dp))
        Text(
            "Mobile Number",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier
                .padding(start = 10.dp, bottom = 10.dp)
        )
        val number = newMobile
        OutlinedTextField(
            value = "+91 | $number",
            onValueChange = {}, // empty
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
        )

        // text for the alternate mobile number
        Spacer(modifier = Modifier.height(15.dp))
        // take the alternating mobile number from user
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "This number will be used to recover your account in case you lose it",
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light,
                ),
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 10.dp)
            )
        }


        Spacer(modifier = Modifier.height(15.dp))
        // button
        Button(
            onClick = {
                // call the api to save changes
                // call the api change the data
                Toast.makeText(context, "updating..", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "Save Changes",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
        }

        // horizontal line
        Spacer(modifier = Modifier.height(15.dp))
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(15.dp))
    }
}
