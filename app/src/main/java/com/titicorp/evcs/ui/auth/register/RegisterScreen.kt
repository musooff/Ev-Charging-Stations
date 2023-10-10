package com.titicorp.evcs.ui.auth.register

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.titicorp.evcs.ui.auth.AuthScreen
import com.titicorp.evcs.ui.auth.login.AuthLoading
import com.titicorp.evcs.ui.auth.login.AuthTextField
import com.titicorp.evcs.ui.auth.login.PasswordField
import com.titicorp.evcs.ui.auth.login.PhoneNumberField
import com.titicorp.evcs.ui.auth.login.navigateToHome

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 20.dp),
        ) {
            Text(
                text = "Sign up",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(20.dp))
            NameField(
                value = uiState.name,
                onValueChange = viewModel::onNameChanged,
            )
            PhoneNumberField(
                value = uiState.phoneNumber,
                onValueChange = viewModel::onPhoneNumberChanged,
            )
            PasswordField(
                value = uiState.password,
                onValueChange = viewModel::onPasswordChanged,
            )
            val notice = buildAnnotatedString {
                append("By signing up, you agree to our ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("Terms and Condition")
                }
                append(" and ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("Privacy Policy")
                }
            }
            ClickableText(
                modifier = Modifier
                    .padding(vertical = 10.dp),
                text = notice,
                style = MaterialTheme.typography.labelSmall,
                onClick = {
                },
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = viewModel::register,
            ) {
                Text(text = "Register")
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Joined EVCS before?")
            TextButton(
                onClick = {
                    navController.navigate(
                        route = AuthScreen.Login.route,
                        navOptions = navOptions {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        },
                    )
                },
            ) {
                Text(text = "Login")
            }
        }
        when (uiState.registerState) {
            RegisterViewModel.UiState.RegisterState.Registering -> {
                val keyboardController = LocalSoftwareKeyboardController.current
                LaunchedEffect(uiState) {
                    keyboardController?.hide()
                }
                AuthLoading()
            }

            RegisterViewModel.UiState.RegisterState.LoggedIn -> {
                LaunchedEffect(Unit) {
                    navigateToHome(navController)
                }
            }

            RegisterViewModel.UiState.RegisterState.Idle -> {}
        }
    }

    BackHandler(
        enabled = uiState.registerState == RegisterViewModel.UiState.RegisterState.Registering,
        onBack = viewModel::cancelRegister,
    )
}

@Composable
private fun NameField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    AuthTextField(
        value = value,
        hint = "Full name",
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .size(20.dp),
                imageVector = Icons.Outlined.Person,
                contentDescription = null,
                tint = Color.Gray,
            )
        },
        onValueChange = onValueChange,
    )
}
