package com.kiarielinus.notes.notes.impl.di

import com.kiarielinus.notes.notes.api.NotesRepository
import com.kiarielinus.notes.notes.impl.data.repository.NotesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun repository(repository: NotesRepositoryImpl): NotesRepository
}