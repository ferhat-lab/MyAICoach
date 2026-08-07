package com.ferhat.myaicoach.feature.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.data.model.AgeRange
import com.ferhat.myaicoach.data.model.Interest

class OnboardingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingState())
    val uiState: StateFlow<OnboardingState> = _uiState.asStateFlow()

    fun nextStep() {
        val state = _uiState.value

        if (
            state.currentStep == OnboardingStep.NICKNAME &&
            state.userProfile.nickname.isBlank()
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
            state.userProfile.ageRange == null
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
            state.userProfile.englishLevel == null
        ) {
            _uiState.update {
                it.copy(
                    errorMessage = "Lütfen İngilizce seviyeni seç."
                )
            }
            return
        }

        if (
            state.currentStep == OnboardingStep.INTERESTS &&
            state.userProfile.interests.size < 3
        ) {
            _uiState.update {
                it.copy(
                    errorMessage = "Devam etmek için en az 3 ilgi alanı seç."
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
                userProfile = it.userProfile.copy(
                    nickname = nickname
                ),
                errorMessage = null
            )
        }
    }

    fun onAgeRangeSelected(ageRange: AgeRange) {
        _uiState.update {
            it.copy(
                userProfile = it.userProfile.copy(
                    ageRange = ageRange
                ),
                errorMessage = null
            )
        }
    }

    fun onEnglishLevelSelected(level: EnglishLevel) {
        _uiState.update {
            it.copy(
                userProfile = it.userProfile.copy(
                    englishLevel = level
                ),
                errorMessage = null
            )
        }
    }

    fun toggleInterest(interest: Interest) {
        _uiState.update { state ->
            val currentInterests = state.userProfile.interests

            val updatedInterests = when {
                interest in currentInterests -> {
                    currentInterests - interest
                }

                currentInterests.size < 5 -> {
                    currentInterests + interest
                }

                else -> {
                    currentInterests
                }
            }

            state.copy(
                userProfile = state.userProfile.copy(
                    interests = updatedInterests
                ),
                errorMessage = if (
                    interest !in currentInterests &&
                    currentInterests.size >= 5
                ) {
                    "En fazla 5 ilgi alanı seçebilirsin."
                } else {
                    null
                }
            )
        }
    }
}