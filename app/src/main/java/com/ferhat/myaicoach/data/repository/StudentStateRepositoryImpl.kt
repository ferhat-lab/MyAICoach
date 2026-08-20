package com.ferhat.myaicoach.data.repository

import com.ferhat.myaicoach.data.local.LocalProgressCache
import com.ferhat.myaicoach.domain.model.StudentState
import com.ferhat.myaicoach.domain.repository.StudentStateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * StudentStateRepositoryImpl: Öğrenci durum verilerini yerel önbellek (LocalProgressCache) ve StateFlow
 * üzerinden yöneten, çevrimdışı (offline) uyumlu repository.
 */
class StudentStateRepositoryImpl(
    private val progressCache: LocalProgressCache? = null
) : StudentStateRepository {

    private val initialStudentState = progressCache?.loadStudentState() ?: StudentState()
    private val _studentState = MutableStateFlow(initialStudentState)
    val studentState: StateFlow<StudentState> = _studentState.asStateFlow()

    override fun getStudentState(): Flow<StudentState> {
        return studentState
    }

    override suspend fun completeLesson(lessonId: String, xpEarned: Int, learnedTargetIds: List<String>) {
        _studentState.update { currentState ->
            val newState = currentState.copy(
                completedLessonIds = currentState.completedLessonIds + lessonId,
                knownTargetIds = currentState.knownTargetIds + learnedTargetIds,
                totalXp = currentState.totalXp + xpEarned,
                completedMinutesToday = (currentState.completedMinutesToday + 10).coerceAtMost(10)
            )
            // Çevrimdışı önbelleğe kaydet
            progressCache?.saveStudentState(newState)
            newState
        }
    }

    override suspend fun markTargetWeak(targetId: String) {
        _studentState.update { currentState ->
            val newState = currentState.copy(
                weakTargetIds = currentState.weakTargetIds + targetId
            )
            // Çevrimdışı önbelleğe kaydet
            progressCache?.saveStudentState(newState)
            newState
        }
    }
}
