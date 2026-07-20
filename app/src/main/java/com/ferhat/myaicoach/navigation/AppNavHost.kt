package com.ferhat.myaicoach.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ferhat.myaicoach.feature.auth.login.LoginRoute
import com.ferhat.myaicoach.feature.auth.register.RegisterScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.Login.route
    ) {
        composable(route = AppRoute.Login.route) {
            LoginRoute(
                onLoginSuccess = {

                },
                onRegisterClick = {
                    navController.navigate(AppRoute.Register.route)
                },
                onForgotPasswordClick = {

                }
            )
        }

        composable(route = AppRoute.Register.route) {
            RegisterScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onLoginClick = {
                    navController.navigate(AppRoute.Login.route) {
                        popUpTo(AppRoute.Register.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}