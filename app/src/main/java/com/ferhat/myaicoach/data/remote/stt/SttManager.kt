package com.ferhat.myaicoach.data.remote.stt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.Locale

/**
 * SttManager: Android yerel SpeechRecognizer ve Whisper STT entegrasyon yöneticisi.
 * Kullanıcı sesini dinler ve parçalı (partial) ile kesinleşmiş (final) metin sonuçlarını yayınlar.
 */
class SttManager(
    private val context: Context
) {
    sealed class SttEvent {
        data class PartialTranscript(val text: String) : SttEvent()
        data class FinalTranscript(val text: String) : SttEvent()
        data class Error(val message: String) : SttEvent()
    }

    private val _sttEvents = MutableSharedFlow<SttEvent>(extraBufferCapacity = 64)
    val sttEvents: SharedFlow<SttEvent> = _sttEvents.asSharedFlow()

    private var speechRecognizer: SpeechRecognizer? = null
    private var isListening: Boolean = false

    init {
        initSpeechRecognizer()
    }

    private fun initSpeechRecognizer() {
        try {
            if (SpeechRecognizer.isRecognitionAvailable(context)) {
                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
                    setRecognitionListener(createListener())
                }
            } else {
                println("⚠️ SpeechRecognizer cihazda mevcut değil.")
            }
        } catch (e: Exception) {
            println("⚠️ SttManager başlatma uyarısı: ${e.localizedMessage}")
        }
    }

    /**
     * Kullanıcı sesini dinlemeyi başlatır.
     */
    fun startListening(languageLocale: Locale = Locale.ENGLISH) {
        if (isListening) return

        try {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageLocale.language)
                putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            }
            speechRecognizer?.startListening(intent)
            isListening = true
            println("🎙️ STT Dinleme Başlatıldı ($languageLocale)")
        } catch (e: Exception) {
            println("⚠️ STT Dinleme başlatma hatası: ${e.localizedMessage}")
        }
    }

    /**
     * Ses dinlemeyi durdurur.
     */
    fun stopListening() {
        if (!isListening) return
        try {
            speechRecognizer?.stopListening()
            isListening = false
            println("⏹️ STT Dinleme Durduruldu.")
        } catch (e: Exception) {
            println("⚠️ STT Dinleme durdurma hatası: ${e.localizedMessage}")
        }
    }

    /**
     * User Barge-In anında STT dinlemesini anında iptal eder.
     */
    fun cancel() {
        try {
            speechRecognizer?.cancel()
            isListening = false
            println("🛑 STT Dinleme Anında İptal Edildi (Barge-In)!")
        } catch (e: Exception) {
            println("⚠️ STT İptal uyarısı: ${e.localizedMessage}")
        }
    }

    private fun createListener(): RecognitionListener {
        return object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {
                isListening = false
            }

            override fun onError(error: Int) {
                isListening = false
                _sttEvents.tryEmit(SttEvent.Error("STT Hatası Kodu: $error"))
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val finalResult = matches?.firstOrNull() ?: ""
                if (finalResult.isNotEmpty()) {
                    _sttEvents.tryEmit(SttEvent.FinalTranscript(finalResult))
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
                val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val partialText = matches?.firstOrNull() ?: ""
                if (partialText.isNotEmpty()) {
                    _sttEvents.tryEmit(SttEvent.PartialTranscript(partialText))
                }
            }

            override fun onEvent(eventType: Int, params: Bundle?) {}
        }
    }

    fun destroy() {
        cancel()
        try {
            speechRecognizer?.destroy()
            speechRecognizer = null
        } catch (e: Exception) {
            println("⚠️ STT destroy uyarısı: ${e.localizedMessage}")
        }
    }
}
