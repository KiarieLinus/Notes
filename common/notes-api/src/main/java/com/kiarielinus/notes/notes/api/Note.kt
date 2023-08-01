package com.kiarielinus.notes.notes.api

import java.time.LocalDateTime

data class Note(
    val id: NoteId,
    val title: String,
    val content: String,
    val dateCreated: LocalDateTime,
    val dateModified: LocalDateTime = dateCreated,
    val color: Int,
    val action: NoteAction?,
)