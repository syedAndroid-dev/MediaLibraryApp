package com.syeddev.medialibraryapp.features.auth.presentation.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syeddev.medialibraryapp.core.theme.MediaLibraryAppTheme

@Preview
@Composable
private fun SignUpScreenPreview() {
    MediaLibraryAppTheme {
        SignUpScreen()
    }
}

@Composable
fun SignUpScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(14.dp),

        ) {
        Spacer(
            modifier = Modifier.statusBarsPadding()
        )

        Text(
            text = "Hey, Hello \uD83D\uDC4B",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )

        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        Text(
            modifier = Modifier.fillMaxWidth(.8f),
            text = "Enter your credentials to access your account",
            fontWeight = FontWeight.W400,
            fontSize = 16.sp
        )
        Spacer(
            modifier = Modifier.padding(20.dp)
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            onValueChange = {},
            placeholder = {
                Text(
                    text = "Name"
                )
            },
            shape = RoundedCornerShape(10.dp)
        )
        Spacer(
            modifier = Modifier.padding(10.dp)
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            onValueChange = {},
            placeholder = {
                Text(
                    text = "Email"
                )
            },
            shape = RoundedCornerShape(10.dp)
        )
        Spacer(
            modifier = Modifier.padding(10.dp)
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            onValueChange = {},
            placeholder = {
                Text(
                    text = "Password"
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        Icons.Default.Visibility,
                        contentDescription = "Visibility"
                    )
                }
            },
            shape = RoundedCornerShape(10.dp)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {},
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = "SignUp",
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))

    }
}