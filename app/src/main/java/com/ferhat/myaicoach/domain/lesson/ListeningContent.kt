package com.ferhat.myaicoach.domain.lesson

data class ListeningContent(
    val id: String,
    val instruction: String,
    val transcript: List<DialogueLine>,
    val questions: List<ListeningQuestion>,
    val targetIds: List<String>
)

data class DialogueLine(
    val speaker: String,
    val text: String
)

data class ListeningQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctAnswer: String,
    val targetIds: List<String>
)