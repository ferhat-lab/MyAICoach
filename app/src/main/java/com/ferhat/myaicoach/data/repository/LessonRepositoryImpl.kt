package com.ferhat.myaicoach.data.repository

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.Lesson
import com.ferhat.myaicoach.domain.lesson.sample.*
import com.ferhat.myaicoach.domain.repository.LessonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * LessonRepositoryImpl: Tüm CEFR A1, A2 ve B1 Müfredat Derslerini (25 Ders + 7 Konuşma Senaryosu) sağlayan repository.
 */
class LessonRepositoryImpl : LessonRepository {

    // Tam Müfredat Ders Kataloğu (A1 12 Ders + A2 12 Ders + B1 1 Ders)
    private val allLessons: List<Lesson> = listOf(
        // CEFR A1 Üniteleri (12 Ders)
        A1Lesson1,
        A1Lesson2,
        A1Lesson3,
        A1Lesson4,
        A1Lesson5,
        A1Lesson6,
        A1Lesson7,
        A1Lesson8,
        A1Lesson9,
        A1Lesson10,
        A1Lesson11,
        A1Lesson12,

        // CEFR A2 Üniteleri (12 Ders)
        A2Lesson1,
        A2Lesson2,
        A2Lesson3,
        A2Lesson4,
        A2Lesson5,
        A2Lesson6,
        A2Lesson7,
        A2Lesson8,
        A2Lesson9,
        A2Lesson10,
        A2Lesson11,
        A2Lesson12,

        // CEFR B1 Üniteleri (1 Ders)
        B1Lesson1
    )

    override fun getAllLessons(): Flow<List<Lesson>> {
        return flowOf(allLessons)
    }

    override fun getLessonById(id: String): Flow<Lesson?> {
        val foundLesson = allLessons.find { it.id == id } ?: allLessons.firstOrNull()
        return flowOf(foundLesson)
    }

    override fun getLessonsByLevel(level: EnglishLevel): Flow<List<Lesson>> {
        val filtered = allLessons.filter { it.level == level }
        return flowOf(if (filtered.isNotEmpty()) filtered else allLessons.filter { it.level == EnglishLevel.A1 })
    }
}
