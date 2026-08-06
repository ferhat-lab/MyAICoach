package com.ferhat.myaicoach.data.model

data class GameProfile(

    val level: Int = 1,

    val xp: Int = 0,

    val coins: Int = 100,

    val streak: Int = 0,

    val totalLessonsCompleted: Int = 0,

    val totalSpeakingSessions: Int = 0
)