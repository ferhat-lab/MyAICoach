package com.ferhat.myaicoach.data.model

data class DailyLesson(
    val id: String,
    val title: String,
    val topic: String,
    val words: List<LessonWord>,
    val sentences: List<LessonSentence>
)

data class LessonWord(
    val english: String,
    val turkish: String,
    val exampleSentence: String
)

data class LessonSentence(
    val english: String,
    val turkish: String
)