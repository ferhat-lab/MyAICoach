package com.ferhat.myaicoach.navigation

sealed class AppRoute(val route: String) {

    data object Login : AppRoute("login")

    data object Register : AppRoute("register")

    data object Onboarding : AppRoute("onboarding")

    data object Home : AppRoute("home")
}