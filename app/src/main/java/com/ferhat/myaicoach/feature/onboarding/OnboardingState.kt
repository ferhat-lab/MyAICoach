package com.ferhat.myaicoach.feature.onboarding

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.data.model.Interest
import com.ferhat.myaicoach.data.model.LearningGoal
import com.ferhat.myaicoach.data.model.AgeRange

data class OnboardingState(

    val currentStep: OnboardingStep = OnboardingStep.NICKNAME,

    val nickname: String = "",

    val nicknameError: String? = null,

    val ageRange: AgeRange? = null,

    val englishLevel: EnglishLevel? = null,

    val interests: List<Interest> = emptyList(),

    val learningGoal: LearningGoal? = null,

    val dailyGoalMinutes: Int? = null,

    val selectedCharacter: String? = null,

    val isLoading: Boolean = false,

    val errorMessage: String? = null
)