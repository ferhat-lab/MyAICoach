package com.ferhat.myaicoach.feature.speaking.audio

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * AudioPlaybackController: Ses çalma ve User Barge-In anında AudioTrack flush/stop işlemlerini yöneten denetleyici.
 */
class AudioPlaybackController {

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    /**
     * Ses akışı çalmayı simüle veya icra eder.
     */
    fun startPlayback(turnId: String) {
        _isPlaying.value = true
        println("🔊 Ses Çalma Başlatıldı (Tur: $turnId)")
    }

    /**
     * User Barge-In veya iptal anında ses çalmayı anında durdurur ve tamponu temizler (Flush).
     */
    fun stopAndFlush() {
        if (_isPlaying.value) {
            _isPlaying.value = false
            println("🛑 AudioTrack Flush & Stop: Ses anında kesildi (Barge-In)!")
        }
    }
}
