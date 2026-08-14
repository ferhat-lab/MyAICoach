package com.ferhat.myaicoach.feature.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ferhat.myaicoach.feature.onboarding.steps.NicknameStep
import com.ferhat.myaicoach.feature.onboarding.steps.AgeStep
import com.ferhat.myaicoach.feature.onboarding.steps.EnglishLevelStep
import com.ferhat.myaicoach.feature.onboarding.steps.InterestStep
import com.ferhat.myaicoach.feature.onboarding.steps.LearningGoalStep
import com.ferhat.myaicoach.feature.onboarding.steps.DailyGoalStep
import com.ferhat.myaicoach.feature.onboarding.steps.AiIntroStep
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.tween

@Composable
fun OnboardingScreen(
    onCompleteClick: () -> Unit,
    viewModel: OnboardingViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val steps = OnboardingStep.entries
    val currentStepIndex = steps.indexOf(state.currentStep)
    val progress = (currentStepIndex + 1).toFloat() / steps.size.toFloat()
    val isFirstStep = state.currentStep == OnboardingStep.NICKNAME
    val isLastStep = state.currentStep == OnboardingStep.AI_INTRO

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 24.dp,
                vertical = 32.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${currentStepIndex + 1} / ${steps.size}",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))

        AnimatedContent(
            targetState = state.currentStep,
            transitionSpec = {
                val initialIndex = steps.indexOf(initialState)
                val targetIndex = steps.indexOf(targetState)

                if (targetIndex > initialIndex) {
                    (
                            slideInHorizontally(
                                animationSpec = tween(250)
                            ) { width ->
                                width / 4
                            } + fadeIn(
                                animationSpec = tween(250)
                            )
                            ) togetherWith (
                            slideOutHorizontally(
                                animationSpec = tween(250)
                            ) { width ->
                                -width / 4
                            } + fadeOut(
                                animationSpec = tween(200)
                            )
                            )
                } else {
                    (
                            slideInHorizontally(
                                animationSpec = tween(250)
                            ) { width ->
                                -width / 4
                            } + fadeIn(
                                animationSpec = tween(250)
                            )
                            ) togetherWith (
                            slideOutHorizontally(
                                animationSpec = tween(250)
                            ) { width ->
                                width / 4
                            } + fadeOut(
                                animationSpec = tween(200)
                            )
                            )
                }
            },
            label = "onboardingStepTransition"
        ) { currentStep ->

            when (currentStep) {

                OnboardingStep.NICKNAME -> {
                    NicknameStep(
                        nickname = state.userProfile.nickname,
                        onNicknameChange = viewModel::onNicknameChange
                    )
                }

                OnboardingStep.AGE -> {
                    AgeStep(
                        selectedAgeRange = state.userProfile.ageRange,
                        onAgeRangeSelected = viewModel::onAgeRangeSelected
                    )
                }

                OnboardingStep.ENGLISH_LEVEL -> {
                    EnglishLevelStep(
                        selectedLevel = state.userProfile.englishLevel,
                        onLevelSelected = viewModel::onEnglishLevelSelected
                    )
                }

                OnboardingStep.INTERESTS -> {
                    InterestStep(
                        selectedInterests = state.userProfile.interests,
                        onInterestClick = viewModel::toggleInterest
                    )
                }

                OnboardingStep.LEARNING_GOAL -> {
                    LearningGoalStep(
                        selectedGoal = state.userProfile.learningGoal,
                        onGoalSelected = viewModel::onLearningGoalSelected
                    )
                }

                OnboardingStep.DAILY_GOAL -> {
                    DailyGoalStep(
                        selectedMinutes = state.userProfile.dailyGoalMinutes,
                        onGoalSelected = viewModel::onDailyGoalSelected
                    )
                }

                OnboardingStep.AI_INTRO -> {
                    AiIntroStep(
                        userProfile = state.userProfile
                    )
                }
            }
        }

        state.errorMessage?.let { error ->
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (!isFirstStep) {
                OutlinedButton(
                    onClick = viewModel::previousStep,
                    modifier = Modifier.weight(1f),
                    enabled = !state.isLoading
                ) {
                    Text(text = "Geri")
                }
            }

            Button(
                onClick = {
                    if (isLastStep) {
                        onCompleteClick()
                    } else {
                        viewModel.nextStep()
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = !state.isLoading
            ) {
                Text(
                    text = if (isLastStep) {
                        "Giriş Yaparak Devam Et"
                    } else {
                        "Devam Et"
                    }
                )
            }
        }
    }
}