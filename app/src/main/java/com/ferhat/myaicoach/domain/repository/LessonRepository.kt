package com.ferhat.myaicoach.domain.repository

import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.Lesson
import kotlinx.coroutines.flow.Flow

/**
 * LessonRepository: Ders müfredatı veri erişim kontratı.
 */
interface LessonRepository {
    fun getAllLessons(): Flow<List<Lesson>>
    fun getLessonById(id: String): Flow<Lesson?>
    fun getLessonsByLevel(level: EnglishLevel): Flow<List<Lesson>>
}
