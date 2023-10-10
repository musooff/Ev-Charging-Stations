package com.titicorp.evcs.ui.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.titicorp.evcs.Screen
import com.titicorp.evcs.ui.auth.login.LoginScreen
import com.titicorp.evcs.ui.auth.register.RegisterScreen

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(startDestination = AuthScreen.Login.route, route = Screen.Auth.route) {
        composable(AuthScreen.Login.route) {
            LoginScreen(navController)
        }
        composable(AuthScreen.Register.route) {
            RegisterScreen(navController)
        }
    }
}

enum class AuthScreen(val route: String) {
    Login("login"),
    Register("register"),
}
