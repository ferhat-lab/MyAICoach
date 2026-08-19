package com.ferhat.myaicoach.feature.speaking.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * AudioPlaybackController: Low-latency VoxCPM2 PCM streaming audio playback motoru.
 * VoxCPM2 V2 resmi spec: 48,000 Hz (out_sample_rate: 48000), 16-bit PCM, Mono, AudioTrack.MODE_STREAM.
 */
class AudioPlaybackController(
    private val sampleRateHz: Int = 48000
) {
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private var audioTrack: AudioTrack? = null
    private val audioQueue = ConcurrentLinkedQueue<ByteArray>()
    private var playbackJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        initAudioTrack()
    }

    private fun initAudioTrack() {
        try {
            val minBufferSize = AudioTrack.getMinBufferSize(
                sampleRateHz,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )

            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()

            val audioFormat = AudioFormat.Builder()
                .setSampleRate(sampleRateHz)
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .build()

            audioTrack = AudioTrack.Builder()
                .setAudioAttributes(audioAttributes)
                .setAudioFormat(audioFormat)
                .setBufferSizeInBytes(minBufferSize * 2)
                .setTransferMode(AudioTrack.MODE_STREAM)
                .build()

            println("🔊 AudioTrack Başlatıldı: $sampleRateHz Hz PCM 16-bit Mono STREAM")
        } catch (e: Exception) {
            println("⚠️ AudioTrack başlatılırken uyarı (Emülatör/Test ortamı): ${e.localizedMessage}")
        }
    }

    /**
     * Akıştan gelen PCM ses verisi paketini (chunk) kuyruğa ekler ve çalmayı başlatır.
     */
    fun enqueueChunk(chunk: ByteArray) {
        audioQueue.add(chunk)
        if (!_isPlaying.value) {
            startPlaybackLoop()
        }
    }

    /**
     * Ses akışı çalmayı başlatır.
     */
    fun startPlayback(turnId: String) {
        _isPlaying.value = true
        println("🔊 VoxCPM2 (48kHz) AudioTrack Çalma Başlatıldı (Tur: $turnId)")
    }

    private fun startPlaybackLoop() {
        _isPlaying.value = true
        playbackJob?.cancel()

        playbackJob = scope.launch {
            try {
                audioTrack?.play()
                while (_isPlaying.value && audioQueue.isNotEmpty()) {
                    val chunk = audioQueue.poll() ?: break
                    audioTrack?.write(chunk, 0, chunk.size)
                }
            } catch (e: Exception) {
                println("⚠️ AudioTrack çalma hatası: ${e.localizedMessage}")
            } finally {
                _isPlaying.value = false
            }
        }
    }

    /**
     * User Barge-In veya İptal anında çalan sesi milisaniye seviyesinde durdurur ve tamponu temizler (Flush).
     */
    fun stopAndFlush() {
        _isPlaying.value = false
        audioQueue.clear()
        playbackJob?.cancel()

        try {
            audioTrack?.let { track ->
                if (track.playState == AudioTrack.PLAYSTATE_PLAYING) {
                    track.pause()
                    track.flush()
                }
            }
            println("🛑 AudioTrack Flush & Stop: Ses tamponu temizlendi ve akış anında kesildi (Barge-In)!")
        } catch (e: Exception) {
            println("⚠️ AudioTrack flush uyarısı: ${e.localizedMessage}")
        }
    }

    /**
     * Kaynakları serbest bırakır.
     */
    fun release() {
        stopAndFlush()
        try {
            audioTrack?.release()
            audioTrack = null
        } catch (e: Exception) {
            println("⚠️ AudioTrack release uyarısı: ${e.localizedMessage}")
        }
    }
}
