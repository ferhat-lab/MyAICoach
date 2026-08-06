package com.ferhat.myaicoach.data.model

data class UserProfile(

    val nickname: String = "",

    val ageRange: AgeRange? = null,

    val englishLevel: EnglishLevel? = null,

    val interests: List<Interest> = emptyList(),

    val learningGoal: LearningGoal? = null,

    val dailyGoalMinutes: Int? = null,

    val selectedCharacter: String? = null
)