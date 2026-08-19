package com.ferhat.myaicoach.navigation

sealed class AppRoute(val route: String) {

    data object Welcome : AppRoute("welcome")

    data object Login : AppRoute("login")

    data object Register : AppRoute("register")

    data object Onboarding : AppRoute("onboarding")

    data object Home : AppRoute("home")

    data object Categories : AppRoute("categories")

    data object Profile : AppRoute("profile")

    data object Speaking : AppRoute("speaking")

    data object Lesson : AppRoute("lesson/{stage}") {

        fun createRoute(stage: String): String {
            return "lesson/$stage"
        }
    }
}