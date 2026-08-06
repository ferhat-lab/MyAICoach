package com.ferhat.myaicoach.feature.onboarding

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.data.model.Interest
import com.ferhat.myaicoach.data.model.LearningGoal
import com.ferhat.myaicoach.data.model.AgeRange
import com.ferhat.myaicoach.data.model.UserProfile

data class OnboardingState(

    val currentStep: OnboardingStep = OnboardingStep.NICKNAME,

    val userProfile: UserProfile = UserProfile(),

    val isLoading: Boolean = false,

    val errorMessage: String? = null
)