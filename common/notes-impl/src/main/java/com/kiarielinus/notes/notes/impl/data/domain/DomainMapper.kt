package com.kiarielinus.notes.notes.impl.data.domain

import com.kiarielinus.notes.notes.api.Note
import com.kiarielinus.notes.notes.impl.data.model.NoteEntity

internal fun NoteEntity.toNote() = Note(
    id = id,
    title = title,
    content = content,
    dateCreated = dateCreated,
    dateModified = dateModified,
    color = color,
    action = action,
)

internal fun Note.toEntity() = NoteEntity(
    id = id,
    title = title,
    content = content,
    dateCreated = dateCreated,
    dateModified = dateModified,
    color = color,
    action = action,
)