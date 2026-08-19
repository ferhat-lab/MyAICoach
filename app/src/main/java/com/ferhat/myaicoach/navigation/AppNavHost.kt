package com.ferhat.myaicoach.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ferhat.myaicoach.feature.auth.login.LoginRoute
import com.ferhat.myaicoach.feature.auth.register.RegisterScreen
import com.ferhat.myaicoach.feature.home.HomeScreen
import com.ferhat.myaicoach.feature.home.LessonStage
import com.ferhat.myaicoach.feature.lesson.LessonCategoryScreen
import com.ferhat.myaicoach.feature.lesson.LessonScreen
import com.ferhat.myaicoach.feature.onboarding.OnboardingScreen
import com.ferhat.myaicoach.feature.onboarding.WelcomeScreen
import com.ferhat.myaicoach.feature.profile.ProfileScreen

/**
 * AppNavHost: Uygulama navigasyon yönlendiricisi.
 * Ana Sayfa, Kategoriler, Dersler ve Profil ekranları arasında akışı yönetir.
 */
@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.Welcome.route
    ) {
        // Karşılama Ekranı
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

        // Giriş Yap Ekranı
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
                onForgotPasswordClick = {}
            )
        }

        // Kayıt Ol Ekranı
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

        // Onboarding Ekranı
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

        // 1. Ana Sayfa Dashboard Ekranı (HomeScreen)
        composable(route = AppRoute.Home.route) {
            HomeScreen(
                onLessonClick = { stage ->
                    if (stage == LessonStage.PRACTICE) {
                        navController.navigate(AppRoute.Categories.route)
                    } else {
                        navController.navigate(
                            AppRoute.Lesson.createRoute(stage.name)
                        )
                    }
                }
            )
        }

        // 2. Ders Kategorileri Seçim Ekranı (LessonCategoryScreen)
        composable(route = AppRoute.Categories.route) {
            LessonCategoryScreen(
                onCategoryClick = { category ->
                    navController.navigate(
                        AppRoute.Lesson.createRoute("PRACTICE")
                    )
                },
                onContinueFeaturedUnit = {
                    navController.navigate(
                        AppRoute.Lesson.createRoute("PRACTICE")
                    )
                }
            )
        }

        // 3-6. Ders Deneyimi ve Egzersiz Ekranları (LessonScreen)
        composable(
            route = AppRoute.Lesson.route
        ) {
            LessonScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // 7. Profil ve Başarımlar Ekranı (ProfileScreen)
        composable(route = AppRoute.Profile.route) {
            ProfileScreen(
                onSettingsClick = {}
            )
        }
    }
}