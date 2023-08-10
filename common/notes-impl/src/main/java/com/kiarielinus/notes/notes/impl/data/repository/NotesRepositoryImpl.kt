package com.kiarielinus.notes.notes.impl.data.repository

import com.kiarielinus.notes.notes.api.Note
import com.kiarielinus.notes.notes.api.NoteAction
import com.kiarielinus.notes.notes.api.NoteId
import com.kiarielinus.notes.notes.api.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class NotesRepositoryImpl @Inject constructor(

): NotesRepository{
    override val notes: Flow<List<Note>>
        get() = TODO("Not yet implemented")

    override suspend fun getNote(noteId: NoteId): Flow<Note> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshNotes() {

    }

    override suspend fun performAction(action: NoteAction?, vararg noteId: NoteId) {
        TODO("Not yet implemented")
    }

    override suspend fun createNote(): Note {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(vararg noteId: NoteId) {
        TODO("Not yet implemented")
    }

    override suspend fun save(vararg note: Note): Array<NoteId> {
        TODO("Not yet implemented")
    }
}