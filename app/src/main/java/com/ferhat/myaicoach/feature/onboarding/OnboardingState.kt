package com.ferhat.myaicoach.feature.onboarding

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.data.model.Interest
import com.ferhat.myaicoach.data.model.LearningGoal

data class OnboardingState(

    val currentStep: OnboardingStep = OnboardingStep.WELCOME,

    val nickname: String = "",

    val age: String = "",

    val englishLevel: EnglishLevel? = null,

    val interests: List<Interest> = emptyList(),

    val learningGoal: LearningGoal? = null,

    val dailyGoalMinutes: Int? = null,

    val selectedCharacter: String? = null,

    val isLoading: Boolean = false,

    val errorMessage: String? = null
)