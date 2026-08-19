package com.ferhat.myaicoach.feature.speaking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferhat.myaicoach.data.remote.streaming.LlmStreamClient
import com.ferhat.myaicoach.data.remote.streaming.VoxCpmStreamClient
import com.ferhat.myaicoach.domain.audio.SpeechSegmenter
import com.ferhat.myaicoach.domain.lesson.SpeakingScenario
import com.ferhat.myaicoach.domain.lesson.sample.A1Scenario1
import com.ferhat.myaicoach.feature.speaking.audio.AudioPlaybackController
import com.ferhat.myaicoach.feature.speaking.turn.TurnController
import com.ferhat.myaicoach.feature.speaking.turn.TurnState
import com.ferhat.myaicoach.feature.speaking.turn.VoiceEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * SpeakingViewModel: Canlı Vani konuşma tur motorunu (Turn State Machine), TurnGuard korumasını, SpeechSegmenter ve Full End-to-End Pipeline'ı yöneten ViewModel.
 * User Barge-In (Araya girme) ve İptal durumlarında ses tamponunu temizleyip durum geçişlerini güvenle tamamlar.
 */
class SpeakingViewModel(
    val turnController: TurnController = TurnController(),
    val audioPlaybackController: AudioPlaybackController = AudioPlaybackController(),
    val speechSegmenter: SpeechSegmenter = SpeechSegmenter(),
    val voxCpmStreamClient: VoxCpmStreamClient = VoxCpmStreamClient(),
    val llmStreamClient: LlmStreamClient = LlmStreamClient()
) : ViewModel() {

    private val _uiState = MutableStateFlow(SpeakingUiState(scenario = A1Scenario1))
    val uiState: StateFlow<SpeakingUiState> = _uiState.asStateFlow()

    private var activeTurnJob: Job? = null

    init {
        observeTurnState()
        observeVoxEvents()
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

    private fun observeVoxEvents() {
        viewModelScope.launch {
            voxCpmStreamClient.eventFlow.collect { event ->
                when (event) {
                    is VoiceEvent.AudioChunk -> {
                        audioPlaybackController.enqueueChunk(event.pcmData)
                    }
                    is VoiceEvent.TurnCancelled -> {
                        cancelTurn()
                    }
                    else -> {}
                }
            }
        }
    }

    /**
     * Mikrofona basıldığında konuşma turunu başlatır. TurnGuard korumasına ve User Barge-In mantığına tabidir.
     */
    fun onMicPress() {
        val scenario = _uiState.value.scenario ?: A1Scenario1

        // 1. Yeni Tur Başlatma İsteği (TurnGuard Debounce Check)
        val newTurn = turnController.startNewTurn(scenario.id) ?: run {
            println("🛡️ TurnGuard: Mikrofona basılma isteği debounce (300ms) engeline takıldı.")
            return
        }

        // 2. Varsa çalışan eski coroutine işini ve çalınan sesi anında durdur (User Barge-In)
        activeTurnJob?.cancel()
        audioPlaybackController.stopAndFlush()
        speechSegmenter.clear()

        _uiState.update { it.copy(isMicPressed = true) }

        // 3. End-to-End Voice Pipeline Akışını Başlat
        activeTurnJob = viewModelScope.launch {
            // A) LISTENING (Kullanıcı Konuşuyor)
            delay(1500)
            if (!turnController.transitionState(newTurn.turnId, TurnState.TRANSCRIBED)) return@launch
            turnController.updateUserTranscript(newTurn.turnId, "Hello Vani! Nice to meet you.")

            // B) LLM_GENERATING & SpeechSegmenter (Düşünme -> MascotState.THINKING)
            delay(800)
            if (!turnController.transitionState(newTurn.turnId, TurnState.LLM_GENERATING)) return@launch
            turnController.updateAiResponse(newTurn.turnId, "Hi there! I am so happy to meet you too!")

            // C) TTS_GENERATING & PLAYING (Low First-Audio Latency -> MascotState.SPEAKING)
            delay(800)
            if (!turnController.transitionState(newTurn.turnId, TurnState.PLAYING)) return@launch
            audioPlaybackController.startPlayback(newTurn.turnId)

            // D) PLAYING (Vani Konuşuyor)
            delay(2500)
            audioPlaybackController.stopAndFlush()

            // E) COMPLETED (Tur Başarıyla Tamamlandı -> MascotState.HAPPY_CHEERING)
            turnController.transitionState(newTurn.turnId, TurnState.COMPLETED)
            _uiState.update { it.copy(isMicPressed = false) }
        }
    }

    /**
     * Kullanıcı konuşmayı tamamladığında veya mikrofondan elini çektiğinde çağrılır.
     */
    fun onMicRelease() {
        _uiState.update { it.copy(isMicPressed = false) }
    }

    /**
     * Kullanıcı Vani konuşurken mikrofona dokunur veya "İptal Et" butonuna basarsa (User Barge-In).
     */
    fun cancelTurn() {
        activeTurnJob?.cancel()
        audioPlaybackController.stopAndFlush()
        speechSegmenter.clear()
        voxCpmStreamClient.sendCancelSignal(_uiState.value.activeTurn?.turnId ?: "", "USER_CANCELLED_MANUALLY")
        turnController.cancelActiveTurn("USER_CANCELLED_MANUALLY")
        _uiState.update { it.copy(isMicPressed = false) }
    }

    fun setScenario(scenario: SpeakingScenario) {
        _uiState.update { it.copy(scenario = scenario) }
    }

    override fun onCleared() {
        super.onCleared()
        audioPlaybackController.release()
        voxCpmStreamClient.disconnect()
    }
}
