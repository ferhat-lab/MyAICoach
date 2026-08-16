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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.text.font.FontWeight

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
            .verticalScroll(rememberScrollState())
            .imePadding()
            .navigationBarsPadding()
            .padding(
                horizontal = 24.dp,
                vertical = 32.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )  {
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

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                if (
                    currentStep != OnboardingStep.NICKNAME &&
                    currentStep != OnboardingStep.AI_INTRO
                ) {
                    Text(
                        text = onboardingStepTitle(currentStep),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = onboardingStepDescription(currentStep),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }

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

private fun onboardingStepTitle(
    step: OnboardingStep
): String {
    return when (step) {
        OnboardingStep.NICKNAME ->
            "Sana nasıl hitap edelim?"

        OnboardingStep.AGE ->
            "Yaş aralığını seç"

        OnboardingStep.ENGLISH_LEVEL ->
            "İngilizce seviyen nedir?"

        OnboardingStep.INTERESTS ->
            "Neler ilgini çekiyor?"

        OnboardingStep.LEARNING_GOAL ->
            "İngilizce öğrenme hedefin ne?"

        OnboardingStep.DAILY_GOAL ->
            "Her gün ne kadar çalışalım?"

        OnboardingStep.AI_INTRO ->
            "Koçun hazır"
    }
}

private fun onboardingStepDescription(
    step: OnboardingStep
): String {
    return when (step) {
        OnboardingStep.NICKNAME ->
            "Derslerde ve konuşma pratiğinde kullanacağımız bir isim seç."

        OnboardingStep.AGE ->
            "Yaşına uygun örnekler ve ders içerikleri hazırlayacağız."

        OnboardingStep.ENGLISH_LEVEL ->
            "Sana en uygun seviyeyi seç. Daha sonra ayarlardan değiştirebilirsin."

        OnboardingStep.INTERESTS ->
            "Derslerini kişiselleştirmek için en az 3, en fazla 5 ilgi alanı seç."

        OnboardingStep.LEARNING_GOAL ->
            "Sana uygun dersleri hazırlayabilmemiz için ana hedefini seç."

        OnboardingStep.DAILY_GOAL ->
            "Sürdürebileceğin günlük çalışma süresini belirle."

        OnboardingStep.AI_INTRO ->
            "Sana özel öğrenme planını hazırlamaya hazırım."
    }
}
