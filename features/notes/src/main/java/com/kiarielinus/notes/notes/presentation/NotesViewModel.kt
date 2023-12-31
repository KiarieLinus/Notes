package com.kiarielinus.notes.notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiarielinus.notes.notes_api.Note
import com.kiarielinus.notes.notes_api.NoteAction
import com.kiarielinus.notes.notes_api.NoteId
import com.kiarielinus.notes.notes_api.NotesRepository
import com.kiarielinus.notes.notes.domain.NoteDomain
import com.kiarielinus.notes.notes.domain.NoteScreen
import com.kiarielinus.notes.settings_api.SettingsRepository
import com.kiarielinus.notes.settings_api.SettingsToggle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NotesViewModel @Inject constructor(
    private val repository: NotesRepository,
    settings: SettingsRepository
) : ViewModel() {

    private val _search = MutableStateFlow("")
    val search = _search.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _search.value,
    )

    private val _selected = MutableStateFlow(listOf<NoteId>())
    val selected = _selected.map {
        it.size
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _selected.value.size,
    )

    private val _screen = MutableStateFlow(NoteScreen.Notes)

    val notes = combine(
        repository.notes,
        _search,
        _selected,
        _screen,
        settings.toggles,
        transform = { notes, filter, selected, screen, settings ->
            notes.screen(screen).search(filter).sort(settings).map(selected)
        }
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = persistentListOf(),
    )

    fun performAction(action: NoteAction?) {
        viewModelScope.launch {
            val notes = _selected.value.toTypedArray()
            //delete if action is Trash and screen is Trash
            if (action == NoteAction.Trash && _screen.value == NoteScreen.Trash) {
                repository.deleteNotes(*notes)
            } else {
                //else put note in trash
                repository.performAction(action, *notes)
            }
            cancelSelection()
        }
    }

    fun search(text: String) {
        viewModelScope.launch {
            _search.emit(text)
        }
    }

    fun toggleSelect(id: NoteId) {
        viewModelScope.launch {
            if (id in _selected.value) {
                _selected.emit(_selected.value - id)
            } else _selected.emit(_selected.value + id)
        }
    }

    fun setScreen(screen: NoteScreen) {
        viewModelScope.launch {
            _screen.emit(screen)
        }
    }

    fun cancelSelection() {
        viewModelScope.launch {
            _selected.emit(emptyList())
        }
    }

    private fun List<Note>.screen(screen: NoteScreen): List<Note> {
        return filter {
            it.action == when (screen) {
                NoteScreen.Archive -> NoteAction.Archive
                NoteScreen.Trash -> NoteAction.Trash
                NoteScreen.Notes -> null
            }
        }
    }

    private fun List<Note>.search(text: String): List<Note> {
        val result = if (text.isNotBlank()) filter {
            it.title.contains(text, true)
                    || it.content.contains(text, true)
        } else this
        return result.toImmutableList()
    }

    private fun List<Note>.sort(settings: Map<SettingsToggle, Boolean>): List<Note> {
        return if (settings[SettingsToggle.AddNewNotesToBottom] == true) {
            sortedBy { it.dateModified }
        } else sortedByDescending { it.dateModified }
    }

    private fun List<Note>.map(selected: List<NoteId>): ImmutableList<NoteDomain> {
        return map {
            NoteDomain(
                note = it,
                selected = it.id in selected
            )
        }.toImmutableList()
    }
}