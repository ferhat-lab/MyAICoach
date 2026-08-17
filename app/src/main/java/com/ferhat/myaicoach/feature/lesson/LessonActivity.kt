package com.ferhat.myaicoach.domain.lesson

sealed interface LessonActivity {

    val id: String
    val targetIds: List<String>
}

data class WordIntroduction(
    override val id: String,
    override val targetIds: List<String>,
    val wordId: String
) : LessonActivity

data class MultipleChoiceActivity(
    override val id: String,
    override val targetIds: List<String>,
    val instruction: String,
    val prompt: String,
    val options: List<String>,
    val correctAnswer: String
) : LessonActivity

data class AudioChoiceActivity(
    override val id: String,
    override val targetIds: List<String>,
    val audioText: String,
    val options: List<String>,
    val correctAnswer: String
) : LessonActivity