package com.ferhat.myaicoach.data.repository

import com.ferhat.myaicoach.domain.model.StudentState
import com.ferhat.myaicoach.domain.repository.StudentStateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * StudentStateRepositoryImpl: Öğrenci durum verilerini hafızada ve StateFlow üzerinden yöneten repository.
 */
class StudentStateRepositoryImpl : StudentStateRepository {

    private val _studentState = MutableStateFlow(StudentState())
    val studentState: StateFlow<StudentState> = _studentState.asStateFlow()

    override fun getStudentState(): Flow<StudentState> {
        return studentState
    }

    override suspend fun completeLesson(lessonId: String, xpEarned: Int, learnedTargetIds: List<String>) {
        _studentState.update { currentState ->
            currentState.copy(
                completedLessonIds = currentState.completedLessonIds + lessonId,
                knownTargetIds = currentState.knownTargetIds + learnedTargetIds,
                totalXp = currentState.totalXp + xpEarned,
                completedMinutesToday = (currentState.completedMinutesToday + 10).coerceAtMost(10)
            )
        }
    }

    override suspend fun markTargetWeak(targetId: String) {
        _studentState.update { currentState ->
            currentState.copy(
                weakTargetIds = currentState.weakTargetIds + targetId
            )
        }
    }
}
