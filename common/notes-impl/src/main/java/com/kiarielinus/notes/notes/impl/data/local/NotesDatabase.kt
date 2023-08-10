package com.kiarielinus.notes.notes.impl.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kiarielinus.notes.notes.impl.data.model.NoteEntity

@TypeConverters(Converters::class)
@Database(entities = [NoteEntity::class], version = 1)
internal abstract class NotesDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDao

    companion object{
        const val DATABASE_NAME = "notes_db"
    }
}