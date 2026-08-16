package com.ferhat.myaicoach.domain.lesson

import com.ferhat.myaicoach.data.model.EnglishLevel

data class LessonUnit(
    val id: String,
    val level: EnglishLevel,
    val order: Int,
    val title: String,
    val description: String,
    val lessons: List<Lesson>
)