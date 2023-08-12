package com.kiarielinus.notes.settings.di

import com.kiarielinus.notes.settings_api.SettingsRepository
import com.kiarielinus.notes.settings.data.repository.SettingsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun repository(repositoryImpl: SettingsRepositoryImpl): SettingsRepository
}