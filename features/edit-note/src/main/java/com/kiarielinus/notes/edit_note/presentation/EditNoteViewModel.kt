package com.kiarielinus.notes.edit_note.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiarielinus.notes.notes_api.Note
import com.kiarielinus.notes.notes_api.NoteAction
import com.kiarielinus.notes.notes_api.NoteId
import com.kiarielinus.notes.notes_api.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
internal class EditNoteViewModel @Inject constructor(
    private val repository: NotesRepository,
) : ViewModel() {

    var title by mutableStateOf("")
        private set

    var content by mutableStateOf("")
        private set

    var editedAt: LocalDateTime by mutableStateOf(LocalDateTime.now())
        private set

    var color by mutableIntStateOf(Note.noteColors.last())
        private set

    private var note: Note? = null

    fun getNote(id: NoteId?) {
        viewModelScope.launch {
            note = id?.let {
                repository.getNote(it).first()
            } ?: repository.createNote()
            title = note?.title ?: ""
            content = note?.content ?: ""
            editedAt = note?.dateModified ?: LocalDateTime.now()
            color = note?.color ?: Note.noteColors.last()
        }
    }

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateContent(content: String){
        this.content = content
    }

    fun updateColor(color: Int){
        this.color = color
    }

    fun performAction(action: NoteAction?) {
        viewModelScope.launch {
            repository.performAction(action, note!!.id)
        }
    }

    fun save() {
        viewModelScope.launch {
            // only save note with changes
            if(note?.title != title || note?.content != content || note?.color != color){
                val id = repository.save(
                    note!!.copy(title = title, content = content, color = color)
                ).firstOrNull() ?: return@launch
                note = repository.getNote(id).first()
            }
        }
    }
}