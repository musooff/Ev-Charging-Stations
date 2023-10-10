package com.titicorp.evcs.ui.auth.login

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.titicorp.evcs.R
import com.titicorp.evcs.Screen
import com.titicorp.evcs.ui.auth.AuthScreen
import com.titicorp.evcs.utils.composables.Loading

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 20.dp),
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(20.dp))
            PhoneNumberField(
                value = uiState.phoneNumber,
                onValueChange = viewModel::onPhoneNumberChanged,
            )

            PasswordField(
                value = uiState.password,
                onValueChange = viewModel::onPasswordChanged,
            )

            TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = { /*TODO*/ },
            ) {
                Text(text = "Forgot Password?")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = viewModel::login,
            ) {
                Text(text = "Login")
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "New to EVCS?")
            TextButton(
                onClick = {
                    navController.navigate(
                        route = AuthScreen.Register.route,
                        navOptions = navOptions {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        },
                    )
                },
            ) {
                Text(text = "Register")
            }
        }
        when (uiState.loginState) {
            LoginViewModel.UiState.LoginState.LoggingIn -> {
                val keyboardController = LocalSoftwareKeyboardController.current
                LaunchedEffect(uiState) {
                    keyboardController?.hide()
                }
                AuthLoading()
            }

            LoginViewModel.UiState.LoginState.LoggedIn -> {
                LaunchedEffect(Unit) {
                    navigateToHome(navController)
                }
            }

            LoginViewModel.UiState.LoginState.Idle -> {}
        }
    }
    BackHandler(
        enabled = uiState.loginState == LoginViewModel.UiState.LoginState.LoggingIn,
        onBack = viewModel::cancelLogin,
    )
}

@Composable
fun PhoneNumberField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    AuthTextField(
        value = value,
        hint = "Phone number",
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .size(20.dp),
                imageVector = Icons.Outlined.Phone,
                contentDescription = null,
                tint = Color.Gray,
            )
        },
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
    )
}

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    var passwordVisible by remember {
        mutableStateOf(false)
    }

    AuthTextField(
        value = value,
        hint = "Password",
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .size(20.dp),
                imageVector = Icons.Outlined.Lock,
                contentDescription = null,
                tint = Color.Gray,
            )
        },
        trailingIcon = {
            val icon = if (passwordVisible) R.drawable.ic_password_visibility else R.drawable.ic_password_visibility_off
            IconButton(onClick = {
                passwordVisible = !passwordVisible
            }) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = Color.Gray,
                )
            }
        },
        onValueChange = onValueChange,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    )
}

@Composable
fun AuthTextField(
    value: String,
    hint: String,
    leadingIcon: @Composable () -> Unit,
    trailingIcon: @Composable () -> Unit = {},
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        leadingIcon()
        BasicTextField(
            modifier = Modifier
                .weight(1f),
            value = value,
            onValueChange = onValueChange,
            decorationBox = {
                if (value.isEmpty()) {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray,
                    )
                }
                it()
            },
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            singleLine = true,
        )
        trailingIcon()
    }
}

@Composable
fun AuthLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.2f)),
    ) {
        Loading()
    }
}

fun navigateToHome(navController: NavController) {
    navController.navigate(
        route = Screen.Home.route,
        navOptions = navOptions {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        },
    )
}
