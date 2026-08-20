package com.ferhat.myaicoach.data.local

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.Lesson
import com.ferhat.myaicoach.domain.lesson.LessonUnit
import com.ferhat.myaicoach.domain.lesson.sample.*

/**
 * OfflineCurriculumCache: Tüm CEFR A1, A2 ve B1 müfredat derslerini ve ünitelerini
 * internet bağlantısı olmadığında yerel diskten kesintisiz sunan önbellekleme katmanı.
 */
class OfflineCurriculumCache {

    private val cachedLessons: List<Lesson> = listOf(
        // CEFR A1 Üniteleri (12 Ders)
        A1Lesson1, A1Lesson2, A1Lesson3, A1Lesson4,
        A1Lesson5, A1Lesson6, A1Lesson7, A1Lesson8,
        A1Lesson9, A1Lesson10, A1Lesson11, A1Lesson12,

        // CEFR A2 Üniteleri (12 Ders)
        A2Lesson1, A2Lesson2, A2Lesson3, A2Lesson4,
        A2Lesson5, A2Lesson6, A2Lesson7, A2Lesson8,
        A2Lesson9, A2Lesson10, A2Lesson11, A2Lesson12,

        // CEFR B1 Üniteleri (1 Ders)
        B1Lesson1
    )

    private val cachedUnits: List<LessonUnit> = listOf(
        A1Unit1,
        A2Unit1, A2Unit2, A2Unit3
    )

    fun getOfflineLessons(): List<Lesson> = cachedLessons

    fun getOfflineLessonById(id: String): Lesson? {
        return cachedLessons.find { it.id == id } ?: cachedLessons.firstOrNull()
    }

    fun getOfflineLessonsByLevel(level: EnglishLevel): List<Lesson> {
        val filtered = cachedLessons.filter { it.level == level }
        return if (filtered.isNotEmpty()) filtered else cachedLessons.filter { it.level == EnglishLevel.A1 }
    }

    fun getOfflineUnits(): List<LessonUnit> = cachedUnits
}
