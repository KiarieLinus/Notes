package com.kiarielinus.notes.notes.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kiarielinus.notes.notes.api.NoteId
import com.kiarielinus.notes.notes.domain.NoteScreen

@Composable
fun NotesRoute(
    modifier: Modifier = Modifier,
    screen: NoteScreen,
    onNoteClick: (NoteId?) -> Unit,
    openDrawer: () -> Unit,
) {
    val viewModel: NotesViewModel = viewModel()

}