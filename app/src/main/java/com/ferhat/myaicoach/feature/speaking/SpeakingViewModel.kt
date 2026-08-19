package com.ferhat.myaicoach.feature.speaking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferhat.myaicoach.domain.lesson.SpeakingScenario
import com.ferhat.myaicoach.domain.lesson.sample.A1Scenario1
import com.ferhat.myaicoach.feature.speaking.audio.AudioPlaybackController
import com.ferhat.myaicoach.feature.speaking.turn.TurnController
import com.ferhat.myaicoach.feature.speaking.turn.TurnState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * SpeakingViewModel: Canlı Vani konuşma tur motorunu (Turn State Machine) ve Mock Voice Pipeline'ı yöneten ViewModel.
 * User Barge-In (Araya girme) ve Iptal durumlarında güvenli durum geçişlerini icra eder.
 */
class SpeakingViewModel(
    val turnController: TurnController = TurnController(),
    val audioPlaybackController: AudioPlaybackController = AudioPlaybackController()
) : ViewModel() {

    private val _uiState = MutableStateFlow(SpeakingUiState(scenario = A1Scenario1))
    val uiState: StateFlow<SpeakingUiState> = _uiState.asStateFlow()

    private var activeTurnJob: Job? = null

    init {
        observeTurnState()
    }

    private fun observeTurnState() {
        viewModelScope.launch {
            turnController.currentTurn.collect { turn ->
                _uiState.update { currentState ->
                    currentState.copy(activeTurn = turn)
                }
            }
        }
    }

    /**
     * Mikrofona basıldığında veya basılı tutulduğunda konuşma turunu başlatır.
     * Varsa aktif çalan sesi anında keser (User Barge-In).
     */
    fun onMicPress() {
        // 1. Varsa çalışan aktif tur coroutine işini ve sesi anında durdur (Barge-In)
        activeTurnJob?.cancel()
        audioPlaybackController.stopAndFlush()

        // 2. Yeni Tur Başlat (LISTENING)
        val scenario = _uiState.value.scenario ?: A1Scenario1
        val newTurn = turnController.startNewTurn(scenario.id)

        _uiState.update { it.copy(isMicPressed = true) }

        // 3. Mock Voice Pipeline Akışını Başlat
        activeTurnJob = viewModelScope.launch {
            // A) LISTENING (1.5s Kullanıcı Konuşuyor Simülasyonu)
            delay(1500)
            if (!turnController.transitionState(newTurn.turnId, TurnState.TRANSCRIBED)) return@launch
            turnController.updateUserTranscript(newTurn.turnId, "Hello Vani! Nice to meet you.")

            // B) LLM_GENERATING (800ms Düşünme Simülasyonu -> MascotState.THINKING)
            delay(800)
            if (!turnController.transitionState(newTurn.turnId, TurnState.LLM_GENERATING)) return@launch
            turnController.updateAiResponse(newTurn.turnId, "Hi there! I am so happy to meet you too!")

            // C) TTS_GENERATING & PLAYING (800ms Ses Üretme & Çalma Simülasyonu -> MascotState.SPEAKING)
            delay(800)
            if (!turnController.transitionState(newTurn.turnId, TurnState.PLAYING)) return@launch
            audioPlaybackController.startPlayback(newTurn.turnId)

            // D) PLAYING (2.5s Vani Konuşuyor)
            delay(2500)
            audioPlaybackController.stopAndFlush()

            // E) COMPLETED (Tur Başarıyla Tamamlandı -> MascotState.HAPPY_CHEERING)
            turnController.transitionState(newTurn.turnId, TurnState.COMPLETED)
            _uiState.update { it.copy(isMicPressed = false) }
        }
    }

    /**
     * Kullanıcı konuşmayı iptal ettiğinde veya mikrofondan elini çektiğinde çağrılır.
     */
    fun onMicRelease() {
        _uiState.update { it.copy(isMicPressed = false) }
    }

    /**
     * Kullanıcı Vani konuşurken "İptal Et" butonuna basarsa (User Barge-In).
     */
    fun cancelTurn() {
        activeTurnJob?.cancel()
        audioPlaybackController.stopAndFlush()
        turnController.cancelActiveTurn("USER_CANCELLED_MANUALLY")
        _uiState.update { it.copy(isMicPressed = false) }
    }

    fun setScenario(scenario: SpeakingScenario) {
        _uiState.update { it.copy(scenario = scenario) }
    }
}
