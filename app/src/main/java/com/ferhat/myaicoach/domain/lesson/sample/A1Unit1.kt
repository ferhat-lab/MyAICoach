package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.LessonUnit

val A1Unit1 = LessonUnit(
    id = "a1_u1",
    level = EnglishLevel.A1,
    order = 1,
    title = "Meeting People",
    description = "Tanışmayı ve kendinden basit şekilde bahsetmeyi öğren.",
    lessons = listOf(
        A1Lesson1
    )
)