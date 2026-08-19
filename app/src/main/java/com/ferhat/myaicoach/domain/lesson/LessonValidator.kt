package com.ferhat.myaicoach.domain.lesson

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}

object LessonValidator {

    /**
     * Lesson ve LessonActivity listesinin bütünlüğünü kontrol eder.
     * Herhangi bir veri tutarsızlığı veya eksik tanımda ValidationResult.Error döndürür.
     */
    fun validate(lesson: Lesson, activities: List<LessonActivity>): ValidationResult {
        if (activities.isEmpty()) {
            return ValidationResult.Error("Ders için hiç aktivite tanımlanmamış.")
        }

        val vocabIds = lesson.vocabulary.map { it.id }.toSet()
        val phraseIds = lesson.phrases.map { it.id }.toSet()
        val grammarIds = lesson.grammarTargets.map { it.id }.toSet()
        val objectiveIds = lesson.objectives.map { it.id }.toSet()

        val validTargetIds = vocabIds + phraseIds + grammarIds + objectiveIds

        activities.forEachIndexed { index, activity ->
            val stepNumber = index + 1

            // 1. Target ID bütünlüğü kontrolü
            activity.targetIds.forEach { targetId ->
                if (targetId !in validTargetIds) {
                    return ValidationResult.Error(
                        "Adım $stepNumber (${activity.id}): Target ID '$targetId' derse ait vocabulary/phrase/grammar listesinde bulunamadı."
                    )
                }
            }

            // 2. Aktivite türüne özel veri bütünlüğü kontrolleri
            when (activity) {
                is WordIntroduction -> {
                    if (activity.wordId !in vocabIds) {
                        return ValidationResult.Error(
                            "Adım $stepNumber (${activity.id}): Kelime tanıtımı için 'wordId' (${activity.wordId}) dersteki kelimeler arasında yok."
                        )
                    }
                }

                is MultipleChoiceActivity -> {
                    if (activity.options.isEmpty()) {
                        return ValidationResult.Error(
                            "Adım $stepNumber (${activity.id}): Çoktan seçmeli soruda hiç seçenek yok."
                        )
                    }
                    if (activity.correctAnswer !in activity.options) {
                        return ValidationResult.Error(
                            "Adım $stepNumber (${activity.id}): Doğru cevap '${activity.correctAnswer}' seçenekler listesinde yer almıyor."
                        )
                    }
                }

                is ReverseChoiceActivity -> {
                    if (activity.options.isEmpty()) {
                        return ValidationResult.Error(
                            "Adım $stepNumber (${activity.id}): Ters seçmeli soruda hiç seçenek yok."
                        )
                    }
                    if (activity.correctAnswer !in activity.options) {
                        return ValidationResult.Error(
                            "Adım $stepNumber (${activity.id}): Doğru cevap '${activity.correctAnswer}' seçenekler listesinde yer almıyor."
                        )
                    }
                }

                is AudioChoiceActivity -> {
                    if (activity.audioText.isBlank()) {
                        return ValidationResult.Error(
                            "Adım $stepNumber (${activity.id}): Dinleme sorusunda audioText boş."
                        )
                    }
                    if (activity.correctAnswer !in activity.options) {
                        return ValidationResult.Error(
                            "Adım $stepNumber (${activity.id}): Doğru cevap '${activity.correctAnswer}' seçenekler listesinde yer almıyor."
                        )
                    }
                }

                is FillInTheBlankActivity -> {
                    if (!activity.sentenceWithBlank.contains("___")) {
                        return ValidationResult.Error(
                            "Adım $stepNumber (${activity.id}): Boşluk doldurma cümlesinde '___' alanı bulunamadı."
                        )
                    }
                    if (activity.correctAnswer !in activity.options) {
                        return ValidationResult.Error(
                            "Adım $stepNumber (${activity.id}): Doğru cevap '${activity.correctAnswer}' seçenekler listesinde yer almıyor."
                        )
                    }
                }

                is SentenceBuilderActivity -> {
                    // Noktalama işaretlerini (. , ? !) temizleyerek normalize etme
                    val chipWordsNormalized = activity.wordChips.map { cleanWord(it) }
                    val targetWords = activity.correctSentence.split(" ").map { cleanWord(it) }

                    targetWords.forEach { targetWord ->
                        if (targetWord !in chipWordsNormalized) {
                            return ValidationResult.Error(
                                "Adım $stepNumber (${activity.id}): Cümle oluşturma çipleri arasında '$targetWord' kelimesi eksik."
                            )
                        }
                    }
                }

                is MatchingActivity -> {
                    if (activity.pairs.isEmpty()) {
                        return ValidationResult.Error(
                            "Adım $stepNumber (${activity.id}): Eşleştirme aktivitesinde hiçbir kelime çifti yok."
                        )
                    }
                }
            }
        }

        return ValidationResult.Success
    }

    private fun cleanWord(word: String): String {
        return word.lowercase().trim().replace(".", "").replace("?", "").replace("!", "").replace(",", "")
    }
}
