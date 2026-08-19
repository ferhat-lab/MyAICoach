package com.ferhat.myaicoach.domain.audio

/**
 * SpeechSegmenter: LLM'den gelen akan metin jetonlarını (tokens) cümle ve yan cümle (clause) bazında bölen motor.
 * Düşük ilk ses gecikmesi (Low first-audio latency) için ilk anlamlı cümlede VoxCPM2 TTS üretimini tetikler.
 */
class SpeechSegmenter {

    private val buffer = StringBuilder()

    /**
     * Akıştan gelen metin jetonunu tampona ekler ve tamamlanan cümle parçalarını döndürür.
     */
    fun appendAndSegment(token: String): List<String> {
        buffer.append(token)
        val text = buffer.toString()
        val segments = mutableListOf<String>()

        // Cümle bitiş işaretleri (. ! ? ; \n)
        val regex = Regex("([^.!?;\n]+[.!?;\n])")
        val matches = regex.findAll(text)

        var lastEnd = 0
        for (match in matches) {
            val segment = match.value.trim()
            if (segment.isNotEmpty()) {
                segments.add(segment)
            }
            lastEnd = match.range.last + 1
        }

        if (lastEnd > 0) {
            buffer.delete(0, lastEnd)
        }

        return segments
    }

    /**
     * Tamponda kalan son parçayı temizler ve döndürür.
     */
    fun flushRemaining(): String? {
        val remaining = buffer.toString().trim()
        buffer.clear()
        return if (remaining.isNotEmpty()) remaining else null
    }

    fun clear() {
        buffer.clear()
    }
}
