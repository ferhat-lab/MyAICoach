package com.ferhat.myaicoach.data.model

enum class Interest(
    val title: String,
    val icon: String
) {

    SOFTWARE(
        title = "Yazılım",
        icon = "💻"
    ),

    ARTIFICIAL_INTELLIGENCE(
        title = "Yapay Zekâ",
        icon = "🤖"
    ),

    TECHNOLOGY(
        title = "Teknoloji",
        icon = "📱"
    ),

    GAMES(
        title = "Oyun",
        icon = "🎮"
    ),

    MOVIES(
        title = "Film",
        icon = "🎬"
    ),

    MUSIC(
        title = "Müzik",
        icon = "🎵"
    ),

    SPORTS(
        title = "Spor",
        icon = "⚽"
    ),

    TRAVEL(
        title = "Seyahat",
        icon = "✈️"
    ),

    BUSINESS(
        title = "İş Dünyası",
        icon = "💼"
    ),

    SCIENCE(
        title = "Bilim",
        icon = "🔬"
    )
}