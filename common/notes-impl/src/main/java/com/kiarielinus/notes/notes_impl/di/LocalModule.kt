package com.kiarielinus.notes.notes_impl.di

import android.content.Context
import androidx.room.Room
import com.kiarielinus.notes.notes_impl.data.local.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class LocalModule {

    @Singleton
    @Provides
    fun database(@ApplicationContext context: Context): NotesDatabase = Room.databaseBuilder(
        context,
        NotesDatabase::class.java,
        NotesDatabase.DATABASE_NAME
    ).build()

    @Provides
    fun notesDao(database: NotesDatabase) = database.notesDao()
}