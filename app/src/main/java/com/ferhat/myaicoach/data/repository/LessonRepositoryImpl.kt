package com.ferhat.myaicoach.data.repository

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.Lesson
import com.ferhat.myaicoach.domain.lesson.sample.A1Lesson1
import com.ferhat.myaicoach.domain.lesson.sample.A1Lesson10
import com.ferhat.myaicoach.domain.lesson.sample.A1Lesson11
import com.ferhat.myaicoach.domain.lesson.sample.A1Lesson12
import com.ferhat.myaicoach.domain.lesson.sample.A1Lesson2
import com.ferhat.myaicoach.domain.lesson.sample.A1Lesson3
import com.ferhat.myaicoach.domain.lesson.sample.A1Lesson4
import com.ferhat.myaicoach.domain.lesson.sample.A1Lesson5
import com.ferhat.myaicoach.domain.lesson.sample.A1Lesson6
import com.ferhat.myaicoach.domain.lesson.sample.A1Lesson7
import com.ferhat.myaicoach.domain.lesson.sample.A1Lesson8
import com.ferhat.myaicoach.domain.lesson.sample.A1Lesson9
import com.ferhat.myaicoach.domain.repository.LessonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * LessonRepositoryImpl: Tüm CEFR A1 Müfredat Derslerini (12 Ders + 3 Konuşma Senaryosu) sağlayan repository.
 */
class LessonRepositoryImpl : LessonRepository {

    // Müfredat Ders Kataloğu (A1 Seviyesi Tam Müfredat: Ünite 1, Ünite 2 ve Ünite 3)
    private val allLessons: List<Lesson> = listOf(
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
        A1Lesson12
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
