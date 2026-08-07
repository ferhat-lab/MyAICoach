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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ferhat.myaicoach.feature.onboarding.steps.NicknameStep
import com.ferhat.myaicoach.feature.onboarding.steps.AgeStep
import com.ferhat.myaicoach.feature.onboarding.steps.EnglishLevelStep
import com.ferhat.myaicoach.feature.onboarding.steps.InterestStep
import com.ferhat.myaicoach.feature.onboarding.steps.LearningGoalStep
import com.ferhat.myaicoach.feature.onboarding.steps.DailyGoalStep
import com.ferhat.myaicoach.feature.onboarding.steps.CharacterStep

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

        when (state.currentStep) {

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

            else -> {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = onboardingStepTitle(state.currentStep),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = onboardingStepDescription(state.currentStep),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
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
                    modifier = Modifier.weight(1f)
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
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = if (isLastStep) {
                        "Başla"
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
        OnboardingStep.NICKNAME -> "Sana nasıl hitap edelim?"
        OnboardingStep.AGE -> "Kaç yaşındasın?"
        OnboardingStep.ENGLISH_LEVEL -> "İngilizce seviyen nedir?"
        OnboardingStep.INTERESTS -> "Neler ilgini çekiyor?"
        OnboardingStep.LEARNING_GOAL -> "İngilizce öğrenme hedefin ne?"
        OnboardingStep.DAILY_GOAL -> "Her gün ne kadar çalışalım?"
        OnboardingStep.AI_INTRO -> "Koçun hazır"
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
            "A1, A2, B1 veya B2 seviyelerinden sana en uygun olanı seç."

        OnboardingStep.INTERESTS ->
            "Ders örneklerini sevdiğin konulara göre kişiselleştireceğiz."

        OnboardingStep.LEARNING_GOAL ->
            "İş, seyahat, okul veya günlük konuşma gibi bir hedef belirle."

        OnboardingStep.DAILY_GOAL ->
            "10, 20, 30 veya daha fazla dakikalık günlük çalışma hedefi seç."

        OnboardingStep.AI_INTRO ->
            "Bilgilerini aldım. Sana özel öğrenme planını oluşturmaya hazırım."
    }
}