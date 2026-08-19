package com.ferhat.myaicoach.domain.lesson.sample

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.LessonUnit

val A2Unit1 = LessonUnit(
    id = "a2_u1",
    level = EnglishLevel.A2,
    order = 1,
    title = "Past Memories & Routines",
    description = "Geçmiş zamanı, anılarını anlatmayı ve günlük rutinlerinle karşılaştırmayı öğren.",
    lessons = listOf(
        A2Lesson1,
        A2Lesson2,
        A2Lesson3,
        A2Lesson4
    )
)
