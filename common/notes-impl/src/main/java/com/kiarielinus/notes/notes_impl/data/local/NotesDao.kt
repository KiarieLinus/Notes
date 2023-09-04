package com.kiarielinus.notes.notes_impl.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kiarielinus.notes.notes_api.NoteId
import com.kiarielinus.notes.notes_impl.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun save(vararg notes: NoteEntity): Array<Long>

    @Query("SELECT * FROM notes")
    abstract fun getNotes() : Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE :id = id")
    abstract fun getNote(id: NoteId): Flow<NoteEntity>

    @Delete
    abstract suspend fun delete(vararg notes: NoteEntity)
}