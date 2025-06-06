package com.syeddev.medialibraryapp.features.auth.presentation.signin

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.syeddev.medialibraryapp.core.navigation.Destination
import com.syeddev.medialibraryapp.core.theme.MediaLibraryAppTheme
import com.syeddev.medialibraryapp.core.components.AnimatedLoader
import com.syeddev.medialibraryapp.core.utils.isValidEmail
import com.syeddev.medialibraryapp.core.utils.isValidPassword
import com.syeddev.medialibraryapp.core.utils.svgicons.ic_smile

@PreviewLightDark
@Composable
private fun SignInScreenPreview() {
    MediaLibraryAppTheme {
        SignInScreenContent(
            isLoading = false,
            userEmail = "",
            userPassword = "",
            onEvent = {},
            isShowError = false
        )
    }
}

@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val signInState = signInViewModel.signInState.collectAsStateWithLifecycle()
    val signInEvent = signInViewModel.event.collectAsStateWithLifecycle(initialValue = SignInUiEvents.Idle)
    val snackBarState = SnackbarHostState()

    LaunchedEffect(signInEvent.value) {
        when (signInEvent.value) {
            SignInUiEvents.Navigate.Home -> {
                Log.e("EventTriggered", "Navigate to Home...")
                navController.navigate(route = Destination.MediaGallery) {
                    popUpTo(0)
                }
            }

            SignInUiEvents.Navigate.SignUp -> {}

            is SignInUiEvents.ShowSnackBar -> {
                snackBarState.showSnackbar(message = (signInEvent.value as SignInUiEvents.ShowSnackBar).message)
            }

            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackBarState) },
    ) { scaffoldPadding ->
        SignInScreenContent(
            modifier = Modifier.padding(scaffoldPadding),
            isLoading = signInState.value.isLoading,
            userEmail = signInState.value.userEmail,
            userPassword = signInState.value.userPassword,
            onEvent = signInViewModel::onEvent,
            isShowError = signInState.value.isShowError
        )
    }
}

@Composable
fun SignInScreenContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    userEmail: String,
    userPassword: String,
    isShowError: Boolean,
    onEvent: (SignInUiEvents) -> Unit,
) {

    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedLoader(isLoading = isLoading)
        Spacer(
            modifier = Modifier.statusBarsPadding()
        )
        Image(
            painter = rememberVectorPainter(ic_smile),
            contentDescription = "Icons",

            )
        Spacer(
            modifier = Modifier.padding(10.dp)
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Welcome back!",
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(
            modifier = Modifier.padding(20.dp)
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = userEmail,
            onValueChange = { email ->
                onEvent(SignInUiEvents.Input.EnterUserEmail(email = email))
            },
            placeholder = {
                Text(
                    text = "Email"
                )
            },
            supportingText = {
                AnimatedVisibility(
                    visible = isShowError && !userEmail.isValidEmail()
                ) {
                    Text(
                        text = "Please Enter Valid Email.",
                        color = MaterialTheme.colorScheme.onError
                    )
                }
            },
            shape = RoundedCornerShape(10.dp),
            isError = isShowError && !userEmail.isValidEmail()
        )
        Spacer(
            modifier = Modifier.padding(10.dp)
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = userPassword,
            onValueChange = { password ->
                onEvent(SignInUiEvents.Input.EnterUserPassword(password = password))
            },
            placeholder = {
                Text(
                    text = "Password"
                )
            },
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(image, contentDescription = if (passwordVisible) "Hide password" else "Show password")
                }
            },
            supportingText = {
                AnimatedVisibility(
                    visible = isShowError && !userPassword.isValidPassword()
                ) {
                    Text(
                        text = "Please Enter Valid Password.",
                        color = MaterialTheme.colorScheme.onError
                    )
                }
            },
            shape = RoundedCornerShape(10.dp),
            isError = isShowError && !userPassword.isValidPassword(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                if (userEmail.isValidEmail() && userPassword.isValidPassword()) {
                    onEvent(SignInUiEvents.ButtonClick.SignIn)
                } else {
                    onEvent(SignInUiEvents.ButtonClick.ChangeErrorState)
                }

            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = "Login",
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onPrimaryContainer

            )
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = "or",
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onPrimaryContainer

            )
        }

        Spacer(
            modifier = Modifier.padding(10.dp)
        )

        Text(
            text = buildAnnotatedString {
                append("Don't have an account? ")
                pushStringAnnotation(
                    tag = "signup",
                    annotation = "signup_click"
                )

                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Sign up")
                }

                pop()
            },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.clickable(onClick = {}),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(
            modifier = Modifier.padding(10.dp)
        )
    }

}