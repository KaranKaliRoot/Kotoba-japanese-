package io.kotoba.app.domain.repository

import io.kotoba.app.data.local.toJlptLevel
import io.kotoba.app.data.local.dao.SettingsDao
import io.kotoba.app.data.local.entity.UserSettingsEntity
import io.kotoba.app.domain.model.JlptLevel
import io.kotoba.app.domain.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private fun UserSettingsEntity.toDomain(): UserSettings = UserSettings(
    onboardingCompleted = onboardingCompleted,
    dailyCardGoal = dailyCardGoal,
    activeLevel = activeLevel.toJlptLevel() ?: JlptLevel.N5,
    theme = theme,
    srsEnabled = srsEnabled,
    showKanaGuide = showKanaGuide,
    lastStudyDay = lastStudyDay,
    streakDays = streakDays
)

private fun UserSettings.toEntity(): UserSettingsEntity = UserSettingsEntity(
    onboardingCompleted = onboardingCompleted,
    dailyCardGoal = dailyCardGoal,
    activeLevel = activeLevel.name,
    theme = theme,
    srsEnabled = srsEnabled,
    showKanaGuide = showKanaGuide,
    lastStudyDay = lastStudyDay,
    streakDays = streakDays
)

class SettingsRepositoryImpl(
    private val settingsDao: SettingsDao
) : SettingsRepository {

    override fun getSettings(): Flow<UserSettings> =
        settingsDao.getSettings().map { it?.toDomain() ?: UserSettings() }

    override suspend fun updateSettings(settings: UserSettings) =
        settingsDao.upsert(settings.toEntity())

    override suspend fun completeOnboarding(settings: UserSettings) =
        settingsDao.upsert(settings.toEntity().copy(onboardingCompleted = true))
}

interface SettingsRepository {
    fun getSettings(): Flow<UserSettings>
    suspend fun updateSettings(settings: UserSettings)
    suspend fun completeOnboarding(settings: UserSettings)
}