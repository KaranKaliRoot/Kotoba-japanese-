package io.kotoba.app.domain.model

data class ReadingStory(
    val id: Long,
    val level: JlptLevel,
    val title: String,
    val text: String,
    val translation: String,
    val vocabIds: List<Long> = emptyList(),
    val source: String = ""
)

data class StoryProgress(
    val storyId: Long,
    val level: JlptLevel,
    val readCount: Int = 0,
    val lastReadAt: Long = 0L
)