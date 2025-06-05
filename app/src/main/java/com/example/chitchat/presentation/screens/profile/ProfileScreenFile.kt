package com.example.chitchat.presentation.screens.profile

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.chitchat.R
import com.example.chitchat.model.navigation.EditDetails
import com.example.chitchat.presentation.formatMillisToDate
import com.example.chitchat.presentation.screens.supports.DisplayInformation
import com.example.chitchat.presentation.viewmodel.AuthViewModel
import com.example.chitchat.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch

// profile of user
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController
) {
    // context
    LocalContext.current
    // for bottom sheet
    val coroutineScope = rememberCoroutineScope()
    mainViewModel.getUserDetails()

    // observe the userdata
    val userInfo = mainViewModel.user.collectAsState().value
    Log.d("user details from viewmodel", " $userInfo")

    // bottom sheet state (maintained to monitor the state )
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    val sheetExpanded = remember { mutableStateOf(false) }

    // for image dialog
    val showDialog = remember { mutableStateOf(false) }

    // launcher for image picker
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri.let {
            /* logic to handle what to do after picking the image
            ** 1. allow the user to crop the image
            ** 2. upload the image to the cloudinary
             */

            // allow the user to crop the image

            // upload the image to cloudinary
            mainViewModel.uploadProfilePicture(uri)

            // pass it to profile viewmodel to recompose
            mainViewModel.getUserDetails()
        }
    }

    // open modal bottom sheet
    if (sheetExpanded.value) {
        ModalBottomSheet(
            onDismissRequest = {
                // change the sheet expanded to false
                sheetExpanded.value = false
            },
            sheetState = bottomSheetState,
            modifier = Modifier.fillMaxWidth(),
            scrimColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.32f)
        ) {
            // Content in the bottom sheet
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            // open a pop-up over the screen
                            showDialog.value = true
                        }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.image_icon),
                        contentDescription = "camera icon",
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(
                        text = "Open Profile Image",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            // pick the image from storage
                            // 1. open image picker
                            imagePickerLauncher.launch("image/*")
                        }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.camera_icon),
                        contentDescription = "camera icon",
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(
                        text = "Change Profile Image",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.delete_icon),
                        contentDescription = "delete icon",
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(
                        text = "Delete",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

            }
        }
    }

    // for image dialog
    if (showDialog.value) {
        Dialog(
            onDismissRequest = { showDialog.value = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(10.dp)
            ) {
                // profile Image
                AsyncImage(
                    model = userInfo.profileImageUrl,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }


    LazyColumn {
        item {
            // Column
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (userInfo.userId.isNullOrEmpty()) {
                    ShimmerEffect(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(top = 40.dp),
                        isCircle = true
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    ShimmerEffect(
                        modifier = Modifier
                            .width(150.dp)
                            .height(30.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ShimmerEffect(
                            modifier = Modifier
                                .width(100.dp)
                                .height(16.dp)
                        )
                        ShimmerEffect(
                            modifier = Modifier
                                .width(100.dp)
                                .height(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ShimmerEffect(
                            modifier = Modifier
                                .weight(0.5f)
                                .height(40.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        ShimmerEffect(
                            modifier = Modifier
                                .weight(0.5f)
                                .height(40.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    ShimmerEffect(
                        modifier = Modifier
                            .width(200.dp)
                            .height(16.dp)
                    )
                } else {
                    // profile image
                    AsyncImage(
                        model = userInfo.profileImageUrl,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(100.dp)
                            .clickable {
                                coroutineScope.launch {
                                    sheetExpanded.value = true
                                }
                            },
                        placeholder = painterResource(R.drawable.icons8_test_account_96),
                        contentScale = ContentScale.Crop
                    )


                    Spacer(modifier = Modifier.height(10.dp))
                    // name of the user
                    Text(
                        text = userInfo?.fullName ?: "N/A", style = TextStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            letterSpacing = 1.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    // spacer
                    Spacer(modifier = Modifier.height(10.dp))
                    // joined date and Username
                    Row() {
                        // text for username
                        Text(
                            text = (userInfo.fullName + userInfo.dateOfBirth) ?: "N/A",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light,
                                letterSpacing = 1.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        // separator
                        VerticalDivider(modifier = Modifier.height(20.dp))

                        // text for joined date
                        Text(text = userInfo.accountCreatedAt.let { formatMillisToDate(it) }
                            ?: "N/A",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light,
                                letterSpacing = 1.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                            modifier = Modifier.padding(start = 10.dp))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    // buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(.8f),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // button for edit profile
                        Button(
                            onClick = {
                                navController.navigate(EditDetails)
                            },
                            modifier = Modifier.weight(.5f),
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
                                "Edit", style = TextStyle(
                                    fontSize = 16.sp,
                                    letterSpacing = 1.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        // button for more
                        Button(
                            onClick = { },
                            modifier = Modifier.weight(.5f),
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
                                "More", style = TextStyle(
                                    fontSize = 16.sp,
                                    letterSpacing = 1.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }
                    }
                    // user bio
                    Text(
                        text = userInfo.bio ?: "", style = TextStyle(
                            fontWeight = FontWeight.Light,
                            fontSize = 16.sp,
                            letterSpacing = 1.sp,
                            textAlign = TextAlign.Center
                        ), modifier = Modifier
                            .fillMaxWidth(.7f)
                            .padding(top = 20.dp, bottom = 10.dp)
                    )


                    // user information
                    DisplayInformation(authViewModel, navController = navController, userInfo)
                }
            }
        }
    }
}

// shimmer effect

@Composable
fun ShimmerEffect(
    modifier: Modifier = Modifier,
    height: Dp = 20.dp,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(4.dp),
    isCircle: Boolean = false
) {
    // Animation for shimmer
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer translation"
    )

    // Gradient brush for shimmer effect
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = translateAnim.value - 200f, y = 0f),
        end = Offset(x = translateAnim.value, y = 0f)
    )

    // Apply shape based on isCircle
    Box(
        modifier = modifier
            .height(height)
            .background(brush, shape = if (isCircle) CircleShape else shape)
    )
}