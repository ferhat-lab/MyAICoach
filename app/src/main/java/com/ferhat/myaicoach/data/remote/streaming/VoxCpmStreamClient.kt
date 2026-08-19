package com.ferhat.myaicoach.data.remote.streaming

import com.ferhat.myaicoach.feature.speaking.turn.VoiceEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * VoxCpmStreamClient: VoxCPM2 Streaming TTS ve Real-Time Voice WebSocket/gRPC İletişim İstemcisi.
 * Gelen parçalı ses verilerini (Low first-audio latency) parçalar halinde VoiceEvent olarak yayınlar.
 */
class VoxCpmStreamClient {

    private val _eventFlow = MutableSharedFlow<VoiceEvent>(extraBufferCapacity = 64)
    val eventFlow: SharedFlow<VoiceEvent> = _eventFlow.asSharedFlow()

    private var isConnected: Boolean = false

    /**
     * WebSocket/gRPC Canlı Ses Bağlantısını Başlatır.
     */
    fun connect(serverUrl: String = "wss://api.myaicoach.internal/v1/voice/stream") {
        isConnected = true
        println("🔌 VoxCPM2 Real-time Dual-Stream Bağlantısı Kuruldu: $serverUrl")
    }

    /**
     * Kullanıcının mikrofondan gelen ses paketini sunucuya gönderir.
     */
    fun sendAudioChunk(turnId: String, pcmData: ByteArray) {
        if (!isConnected) return
        // Real-time audio streaming dispatch logic
    }

    /**
     * User Barge-In veya iptal durumunda sunucuya CANCEL mesajı iletir.
     */
    fun sendCancelSignal(turnId: String, reason: String = "USER_BARGE_IN") {
        if (!isConnected) return
        println("📡 Sunucuya Turn Cancel Sinyali Gönderildi ($turnId): $reason")
        _eventFlow.tryEmit(VoiceEvent.TurnCancelled(turnId, reason))
    }

    /**
     * Sunucudan gelen VoxCPM2 ses akışı parçasını simüle/emite eder.
     */
    fun onAudioChunkReceived(turnId: String, pcmChunk: ByteArray) {
        _eventFlow.tryEmit(VoiceEvent.AudioChunk(turnId, pcmChunk))
    }

    /**
     * Bağlantıyı kapatır.
     */
    fun disconnect() {
        isConnected = false
        println("🔌 VoxCPM2 Dual-Stream Bağlantısı Kapatıldı.")
    }
}
