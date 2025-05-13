package com.example.chitchat.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chitchat.R
import com.example.chitchat.model.chat.Chats


@Composable
fun ChatScreen(modifier: Modifier) {
    // which section is selected
    var section by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // another box for switching between the message and archived message
        Box(
            modifier = Modifier
                .border(2.dp, Color(0xFFEE8259), RoundedCornerShape(20.dp))
                .padding(start = 2.dp, top = 2.dp, bottom = 2.dp, end = 2.dp)
                .fillMaxWidth(.8f)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            // create row that will contain these buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                // button 1 for message
                OutlinedButton(
                    onClick = {
                        // select the current section
                        section = 1
                        // check if this button is selected do nothing
                        // display the chats of the user
                    },
                    modifier = Modifier
                        .weight(.5f),
                    shape = RoundedCornerShape(15.dp), // Corner radius
                    border = if (section == 1) BorderStroke(
                        2.dp,
                        Color(0xFFEE8259)
                    ) else BorderStroke(2.dp, Color.Transparent),  // Border color and width
                    colors = ButtonDefaults.buttonColors(
                        if(section == 1) Color(0xFFEE8259) else MaterialTheme.colorScheme.background
                    )
                ) {
                    // text
                    Text(
                        "Message",
                        color = if(section == 1)Color.White else Color(0xFFEE8259)
                    )
                }
                Spacer(modifier = Modifier.width(2.dp))
                // button 2 for archived message
                OutlinedButton(
                    onClick = {
                        // select the current section
                        section = 2
                    },
                    modifier = Modifier
                        .weight(.5f),
                    shape = RoundedCornerShape(15.dp), // Corner radius
                    border = if (section == 2) BorderStroke(
                        2.dp,
                        Color(0xFFEE8259)
                    ) else BorderStroke(2.dp, Color.Transparent), // Border color and width
                    colors = ButtonDefaults.buttonColors(
                        if(section == 2) Color(0xFFEE8259) else MaterialTheme.colorScheme.background
                    )
                ) {
                    Text(
                        "Archived",
                        color = if(section == 2)Color.White else Color(0xFFEE8259)
                    )
                }
            }
        }

        // on bases of section selected show the screen
        if (section == 1) {
            DisplayChats()
        } else {
            DisplayArchivedMessages()
        }

    }

}

@Composable
fun ChatItem(userName: String, lastMessage: String, time: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 12.dp)
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Picture
        Icon(
            painter = painterResource(R.drawable.icons8_test_account_96),
            contentDescription = "Profile picture",
            tint = Color.Unspecified,
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Username and Last Message Column
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = userName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = lastMessage,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Time and Tick Icon
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = time,
                fontSize = 12.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Message status"
            )
        }
    }
}

// function to show the Chats on the page
@Composable
fun DisplayChats() {
    // fetch the chats -> use viewmodel, states, flow to observe the changes

    // records for testing
    val chatList = listOf(
        Chats("Rahul", "Kya haal hai?", "10:00 AM"),
        Chats("Rahul", "Kya haal hai?", "10:00 AM"),
        Chats("Rahul", "Kya haal hai?", "10:00 AM"),
        Chats("Rahul", "Kya haal hai?", "10:00 AM"),
        Chats("Priya", "Let's meet tomorrow", "9:30 AM"),
        Chats("Ankit", "Code bhej diya", "8:45 AM"),
        Chats("Ankit", "Code bhej diya", "8:45 AM"),
        Chats("Ankit", "Code bhej diya", "8:45 AM"),
        Chats("Ankit", "Code bhej diya", "8:45 AM"),
        Chats("Ankit", "Code bhej diya", "8:45 AM"),
        Chats("Ankit", "Code bhej diya", "8:45 AM"),
        Chats("Ankit", "Code bhej diya", "8:45 AM"),
        Chats("Ankit", "Code bhej diya", "8:45 AM"),
        Chats("Ankit", "Code bhej diya", "8:45 AM"),
        Chats("Ankit", "Code bhej diya", "8:45 AM"),
    )
    // box for containing the chat items
    Spacer(modifier = Modifier.height(5.dp))
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // lazy column
        LazyColumn(

        ) {
            items(chatList) { items ->
                ChatItem(
                    userName = items.userName,
                    lastMessage = items.lastMessage,
                    time = items.time
                )
                // divider after every item in the list
                HorizontalDivider(modifier = Modifier.padding(start = 16.dp, end = 16.dp))
            }
        }
    }

}

// function to show the archived messages on the page
@Composable
fun DisplayArchivedMessages() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "No Archived messages because this section is under dev...",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
        )
    }
}

