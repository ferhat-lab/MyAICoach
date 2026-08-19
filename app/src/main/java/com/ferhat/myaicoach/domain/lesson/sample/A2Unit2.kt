package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.LessonUnit

val A2Unit2 = LessonUnit(
    id = "a2_u2",
    level = EnglishLevel.A2,
    order = 2,
    title = "Travel, Hotels & Directions",
    description = "Havaalanı, otel rezervasyonu, şehirde yol tarifi sorma ve tur planlama becerilerini edin.",
    lessons = listOf(
        A2Lesson5,
        A2Lesson6,
        A2Lesson7,
        A2Lesson8
    )
)
