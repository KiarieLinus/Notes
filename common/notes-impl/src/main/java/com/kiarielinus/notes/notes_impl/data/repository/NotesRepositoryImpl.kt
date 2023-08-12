package com.kiarielinus.notes.notes_impl.data.repository

import com.kiarielinus.notes.notes_api.Note
import com.kiarielinus.notes.notes_api.NoteAction
import com.kiarielinus.notes.notes_api.NoteId
import com.kiarielinus.notes.notes_api.NotesRepository
import com.kiarielinus.notes.notes_impl.data.domain.toEntity
import com.kiarielinus.notes.notes_impl.data.domain.toNote
import com.kiarielinus.notes.notes_impl.data.local.NotesDao
import com.kiarielinus.notes.notes_impl.data.model.NoteEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

internal class NotesRepositoryImpl @Inject constructor(
    private val local: NotesDao
) : NotesRepository {
    override val notes: Flow<List<Note>>
        get() = local.getNotes().map { notes ->
            notes.map { it.toNote() }
        }

    override suspend fun getNote(noteId: NoteId): Flow<Note> =
        local.getNote(noteId).map { it.toNote() }

    override suspend fun refreshNotes() {

    }

    override suspend fun performAction(
        action: NoteAction?,
        vararg noteId: NoteId
    ) {
        val notes = notes.map { notes ->
            notes.filter {
                it.id in noteId
            }.map { note ->
                note.copy(action = action).toEntity()
            }
        }.first()
        local.save(*notes.toTypedArray())
    }

    override suspend fun createNote() = NoteEntity().toNote()

    override suspend fun deleteNotes(vararg noteId: NoteId) {
        val notes = local.getNotes().first()

        local.delete(*notes.filter { it.id in noteId }.toTypedArray())
    }

    override suspend fun save(vararg note: Note): Array<NoteId> = coroutineScope {
        val filter: (Note) -> Boolean = {
            it.content.isBlank() && it.title.isBlank()
        }

        //save notes with content
        val notesToSave = note.filterNot(filter).map {
            it.toEntity().copy(dateModified = LocalDateTime.now())
        }

        //remove notes without content
        val notesToRemove = note.filter(filter).map { it.toEntity() }
        val remove = async { local.delete(*notesToRemove.toTypedArray()) }
        val saved = local.save(*notesToSave.toTypedArray())
        remove.await()
        return@coroutineScope saved.map { NoteId(it) }.toTypedArray()
    }
}