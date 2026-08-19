package com.ferhat.myaicoach.domain.repository

import com.ferhat.myaicoach.domain.model.StudentState
import kotlinx.coroutines.flow.Flow

/**
 * StudentStateRepository: Öğrenci durum takibi arayüzü.
 */
interface StudentStateRepository {
    fun getStudentState(): Flow<StudentState>
    suspend fun completeLesson(lessonId: String, xpEarned: Int, learnedTargetIds: List<String>)
    suspend fun markTargetWeak(targetId: String)
}
