package com.ferhat.myaicoach.data.repository

import com.ferhat.myaicoach.data.local.OfflineCurriculumCache
import com.ferhat.myaicoach.data.model.EnglishLevel
import com.ferhat.myaicoach.domain.lesson.Lesson
import com.ferhat.myaicoach.domain.repository.LessonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * LessonRepositoryImpl: Tüm CEFR A1, A2 ve B1 Müfredat Derslerini yerel çevrimdışı önbellekten (OfflineCurriculumCache) sağlayan repository.
 */
class LessonRepositoryImpl(
    private val curriculumCache: OfflineCurriculumCache = OfflineCurriculumCache()
) : LessonRepository {

    override fun getAllLessons(): Flow<List<Lesson>> {
        return flowOf(curriculumCache.getOfflineLessons())
    }

    override fun getLessonById(id: String): Flow<Lesson?> {
        return flowOf(curriculumCache.getOfflineLessonById(id))
    }

    override fun getLessonsByLevel(level: EnglishLevel): Flow<List<Lesson>> {
        return flowOf(curriculumCache.getOfflineLessonsByLevel(level))
    }
}
