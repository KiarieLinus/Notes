package com.kiarielinus.notes.notes.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kiarielinus.notes.notes_api.NoteId
import com.kiarielinus.notes.notes.domain.NoteScreen

@Composable
fun NotesRoute(
    modifier: Modifier = Modifier,
    screen: NoteScreen,
    onNoteClick: (NoteId?) -> Unit,
    openDrawer: () -> Unit,
) {
    val viewModel: NotesViewModel = viewModel()
    val search by viewModel.search.collectAsState()
    val notes by viewModel.notes.collectAsState()
    val selected by viewModel.selected.collectAsState()

    LaunchedEffect(key1 = screen) {
        viewModel.setScreen(screen)
    }

    NotesScreen(
        modifier = modifier,
        search = search,
        notes = notes,
        selected = selected,
        screen = screen,
        editNote = onNoteClick,
        performAction = {
            viewModel.performAction(it)
        },
        openDrawer = openDrawer,
        onSearch = viewModel::search,
        onSelectedChanged = viewModel::toggleSelect,
        cancelSelection = viewModel::cancelSelection,
    )
}