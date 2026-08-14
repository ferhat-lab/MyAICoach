package com.ferhat.myaicoach.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ferhat.myaicoach.feature.auth.login.LoginRoute
import com.ferhat.myaicoach.feature.auth.register.RegisterScreen
import com.ferhat.myaicoach.feature.home.HomeScreen
import com.ferhat.myaicoach.feature.onboarding.OnboardingScreen
import com.ferhat.myaicoach.feature.onboarding.WelcomeScreen
import com.ferhat.myaicoach.feature.lesson.LessonScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.Welcome.route
    ) {
        composable(route = AppRoute.Welcome.route) {
            WelcomeScreen(
                onStartClick = {
                    navController.navigate(AppRoute.Onboarding.route)
                },
                onExistingAccountClick = {
                    navController.navigate(AppRoute.Login.route)
                }
            )
        }

        composable(route = AppRoute.Login.route) {
            LoginRoute(
                onLoginSuccess = {
                    navController.navigate(AppRoute.Home.route) {
                        popUpTo(AppRoute.Login.route) {
                            inclusive = true
                        }
                    }
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

        composable(route = AppRoute.Onboarding.route) {
            OnboardingScreen(
                onCompleteClick = {
                    navController.navigate(AppRoute.Login.route) {
                        popUpTo(AppRoute.Onboarding.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = AppRoute.Home.route) {
            HomeScreen(
                onLessonClick = { stage ->
                    navController.navigate(
                        AppRoute.Lesson.createRoute(stage.name)
                    )
                }
            )
        }

        composable(
            route = AppRoute.Lesson.route
        ) { backStackEntry ->

            val stageName = backStackEntry.arguments
                ?.getString("stage")
                ?: return@composable

            LessonScreen(
                stageName = stageName
            )
        }
    }
}