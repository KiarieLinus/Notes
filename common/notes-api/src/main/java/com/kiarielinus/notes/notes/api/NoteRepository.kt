package com.kiarielinus.notes.notes.api

import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    val notes: Flow<List<Note>>
}