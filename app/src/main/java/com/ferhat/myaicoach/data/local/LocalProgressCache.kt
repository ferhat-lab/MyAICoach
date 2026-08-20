package com.ferhat.myaicoach.data.local

import android.content.Context
import android.content.SharedPreferences
import com.ferhat.myaicoach.domain.model.StudentState
import org.json.JSONArray
import org.json.JSONObject

/**
 * LocalProgressCache: Öğrenci XP, Seri (Streak), Tamamlanan Dersler ve Zayıf/Öğrenilmiş Hedefleri
 * cihaz hafızasında (Offline Storage) saklayan önbellekleme katmanı.
 */
class LocalProgressCache(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("myaicoach_offline_progress", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TOTAL_XP = "total_xp"
        private const val KEY_STREAK_DAYS = "streak_days"
        private const val KEY_COMPLETED_MINUTES = "completed_minutes_today"
        private const val KEY_DAILY_GOAL = "daily_goal_minutes"
        private const val KEY_COMPLETED_LESSONS = "completed_lessons_json"
        private const val KEY_KNOWN_TARGETS = "known_targets_json"
        private const val KEY_WEAK_TARGETS = "weak_targets_json"
    }

    /**
     * Yerel veritabanında saklanan öğrenci durumunu yükler.
     */
    fun loadStudentState(): StudentState {
        val totalXp = prefs.getInt(KEY_TOTAL_XP, 0)
        val streakDays = prefs.getInt(KEY_STREAK_DAYS, 7)
        val completedMinutes = prefs.getInt(KEY_COMPLETED_MINUTES, 8)
        val dailyGoal = prefs.getInt(KEY_DAILY_GOAL, 10)

        val completedLessons = jsonArrayToList(prefs.getString(KEY_COMPLETED_LESSONS, "[]") ?: "[]").toSet()
        val knownTargets = jsonArrayToList(prefs.getString(KEY_KNOWN_TARGETS, "[]") ?: "[]").toSet()
        val weakTargets = jsonArrayToList(prefs.getString(KEY_WEAK_TARGETS, "[]") ?: "[]").toSet()

        return StudentState(
            completedLessonIds = completedLessons,
            knownTargetIds = knownTargets,
            weakTargetIds = weakTargets,
            totalXp = totalXp,
            streakDays = streakDays,
            completedMinutesToday = completedMinutes,
            dailyGoalMinutes = dailyGoal
        )
    }

    /**
     * Güncellenen öğrenci durumunu çevrimdışı önbelleğe kaydeder.
     */
    fun saveStudentState(state: StudentState) {
        prefs.edit().apply {
            putInt(KEY_TOTAL_XP, state.totalXp)
            putInt(KEY_STREAK_DAYS, state.streakDays)
            putInt(KEY_COMPLETED_MINUTES, state.completedMinutesToday)
            putInt(KEY_DAILY_GOAL, state.dailyGoalMinutes)
            putString(KEY_COMPLETED_LESSONS, listToJsonArray(state.completedLessonIds.toList()))
            putString(KEY_KNOWN_TARGETS, listToJsonArray(state.knownTargetIds.toList()))
            putString(KEY_WEAK_TARGETS, listToJsonArray(state.weakTargetIds.toList()))
            apply()
        }
    }

    private fun listToJsonArray(list: List<String>): String {
        val jsonArray = JSONArray()
        list.forEach { jsonArray.put(it) }
        return jsonArray.toString()
    }

    private fun jsonArrayToList(jsonStr: String): List<String> {
        val list = mutableListOf<String>()
        try {
            val jsonArray = JSONArray(jsonStr)
            for (i in 0 until jsonArray.length()) {
                list.add(jsonArray.getString(i))
            }
        } catch (e: Exception) {
            println("⚠️ Offline JSON parse uyarısı: ${e.localizedMessage}")
        }
        return list
    }
}
