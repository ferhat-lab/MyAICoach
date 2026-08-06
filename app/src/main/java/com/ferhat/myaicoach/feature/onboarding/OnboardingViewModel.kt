package com.ferhat.myaicoach.feature.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.data.model.AgeRange

class OnboardingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingState())
    val uiState: StateFlow<OnboardingState> = _uiState.asStateFlow()

    fun nextStep() {
        val state = _uiState.value

        if (
            state.currentStep == OnboardingStep.NICKNAME &&
            state.nickname.isBlank()
        ) {
            _uiState.update {
                it.copy(
                    errorMessage = "Lütfen sana hitap edebilmem için bir isim gir."
                )
            }
            return
        }

        if (
            state.currentStep == OnboardingStep.AGE &&
            state.ageRange == null
        ) {
            _uiState.update {
                it.copy(
                    errorMessage = "Lütfen yaş aralığını seç."
                )
            }
            return
        }

        if (
            state.currentStep == OnboardingStep.ENGLISH_LEVEL &&
            state.englishLevel == null
        ) {
            _uiState.update {
                it.copy(
                    errorMessage = "Lütfen İngilizce seviyeni seç."
                )
            }
            return
        }

        val steps = OnboardingStep.entries
        val currentIndex = steps.indexOf(state.currentStep)

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

    fun onAgeRangeSelected(ageRange: AgeRange) {
        _uiState.update {
            it.copy(
                ageRange = ageRange,
                errorMessage = null
            )
        }
    }

    fun onEnglishLevelSelected(
        level: EnglishLevel
    ) {
        _uiState.update {
            it.copy(
                englishLevel = level,
                errorMessage = null
            )
        }
    }
}