package com.ferhat.myaicoach.data.model

data class UserProfile(

    val nickname: String = "",

    val age: Int = 18,

    val englishLevel: EnglishLevel = EnglishLevel.A1,

    val learningGoal: LearningGoal = LearningGoal.DAILY_CONVERSATION,

    val interests: List<Interest> = emptyList(),

    val dailyGoalMinutes: Int = 20,

    val xp: Int = 0,

    val coins: Int = 100,

    val streak: Int = 0
)