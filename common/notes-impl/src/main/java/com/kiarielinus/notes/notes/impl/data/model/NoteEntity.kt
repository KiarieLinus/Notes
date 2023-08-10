package com.kiarielinus.notes.notes.impl.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kiarielinus.notes.notes.api.Note
import com.kiarielinus.notes.notes.api.NoteAction
import com.kiarielinus.notes.notes.api.NoteId
import java.time.LocalDateTime

@Entity(tableName = "notes")
internal data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: NoteId = NoteId(0L),
    val title: String = "",
    val content: String = "",
    val dateCreated: LocalDateTime = LocalDateTime.now(),
    val dateModified: LocalDateTime = dateCreated,
    val color: Int = Note.noteColors.last(),
    val action: NoteAction? = null,
)
