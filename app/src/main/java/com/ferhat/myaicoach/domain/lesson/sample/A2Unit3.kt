package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.LessonUnit

val A2Unit3 = LessonUnit(
    id = "a2_u3",
    level = EnglishLevel.A2,
    order = 3,
    title = "Career, Shopping & Graduation",
    description = "Meslekler, iş mülakatı hazırlığı, kıyafet alışverişi ve A2 seviye mezuniyet meydan okuması.",
    lessons = listOf(
        A2Lesson9,
        A2Lesson10,
        A2Lesson11,
        A2Lesson12
    )
)
