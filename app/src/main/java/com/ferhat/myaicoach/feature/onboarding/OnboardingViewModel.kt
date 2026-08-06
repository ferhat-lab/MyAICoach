package com.ferhat.myaicoach.feature.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OnboardingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingState())
    val uiState: StateFlow<OnboardingState> = _uiState.asStateFlow()

    fun nextStep() {
        val steps = OnboardingStep.entries
        val currentIndex = steps.indexOf(_uiState.value.currentStep)

        if (currentIndex < steps.lastIndex) {
            _uiState.update {
                it.copy(
                    currentStep = steps[currentIndex + 1],
                    errorMessage = null
                )
            }
        }
    }

    fun previousStep() {
        val steps = OnboardingStep.entries
        val currentIndex = steps.indexOf(_uiState.value.currentStep)

        if (currentIndex > 0) {
            _uiState.update {
                it.copy(
                    currentStep = steps[currentIndex - 1],
                    errorMessage = null
                )
            }
        }
    }

    fun onNicknameChange(nickname: String) {
        _uiState.update {
            it.copy(
                nickname = nickname,
                errorMessage = null
            )
        }
    }
}