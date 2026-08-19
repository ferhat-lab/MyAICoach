package com.ferhat.myaicoach.data.remote.streaming

import com.ferhat.myaicoach.feature.speaking.turn.VoiceEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * LlmStreamClient: Qwen / Gemini LLM metin jetonu (token-by-token streaming) ve cümle bölüntüleme (SpeechSegmenter) istemcisi.
 * İlk ses gecikmesini (Low first-audio latency) düşürmek için tamamlanan ilk cümlede TTS üretimini anında tetikler.
 */
class LlmStreamClient {

    private val _eventFlow = MutableSharedFlow<VoiceEvent>(extraBufferCapacity = 64)
    val eventFlow: SharedFlow<VoiceEvent> = _eventFlow.asSharedFlow()

    private val tokenBuffer = StringBuilder()

    /**
     * LLM jetonlarını (tokens) parçalar halinde işler ve nokta/virgül bazlı cümle parçalarına böler (SpeechSegmenter).
     */
    fun processIncomingToken(turnId: String, token: String) {
        tokenBuffer.append(token)
        val currentText = tokenBuffer.toString()

        // İlk tamamlanan cümle tespiti (. ! ?)
        if (currentText.contains(".") || currentText.contains("!") || currentText.contains("?")) {
            val sentence = currentText.trim()
            println("💬 LLM Cümle Segmenti Tamamlandı ($turnId): \"$sentence\" -> VoxCPM2 TTS'e Aktarılıyor...")
            _eventFlow.tryEmit(VoiceEvent.SpeechSegment(turnId, sentence))
            tokenBuffer.clear()
        }
    }

    fun resetBuffer() {
        tokenBuffer.clear()
    }
}
