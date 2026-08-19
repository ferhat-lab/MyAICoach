package com.ferhat.myaicoach.data.repository

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.Lesson
import com.ferhat.myaicoach.domain.lesson.sample.A1Lesson1
import com.ferhat.myaicoach.domain.lesson.sample.A1Lesson2
import com.ferhat.myaicoach.domain.lesson.sample.A1Lesson3
import com.ferhat.myaicoach.domain.lesson.sample.A1Lesson4
import com.ferhat.myaicoach.domain.lesson.sample.A1Lesson5
import com.ferhat.myaicoach.domain.repository.LessonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * LessonRepositoryImpl: Müfredat derslerini sağlayan repository.
 */
class LessonRepositoryImpl : LessonRepository {

    // Müfredat Ders Kataloğu (A1 Seviye Ünite 1 ve Ünite 2 Dersleri)
    private val allLessons: List<Lesson> = listOf(
        A1Lesson1,
        A1Lesson2,
        A1Lesson3,
        A1Lesson4,
        A1Lesson5
    )

    override fun getAllLessons(): Flow<List<Lesson>> {
        return flowOf(allLessons)
    }

    override fun getLessonById(id: String): Flow<Lesson?> {
        val foundLesson = allLessons.find { it.id == id } ?: allLessons.firstOrNull()
        return flowOf(foundLesson)
    }

    override fun getLessonsByLevel(level: EnglishLevel): Flow<List<Lesson>> {
        return flowOf(allLessons.filter { it.level == level })
    }
}
