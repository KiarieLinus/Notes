package com.kiarielinus.notes.notes.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kiarielinus.notes.notes.R
import com.kiarielinus.notes.notes_api.Note
import com.kiarielinus.notes.notes_api.NoteAction
import com.kiarielinus.notes.notes_api.NoteId
import com.kiarielinus.notes.notes.domain.NoteDomain
import com.kiarielinus.notes.notes.domain.NoteScreen
import com.kiarielinus.notes.notes.presentation.component.NotesAppBar
import com.kiarielinus.notes.notes.presentation.component.NotesInSelectionAppBar
import com.kiarielinus.notes.notes.presentation.component.NotesItem
import com.kiarielinus.notes.ui.NoteActionDialog
import com.kiarielinus.notes.ui.theme.NotesTheme
import com.kiarielinus.notes.ui.theme.padding
import com.kiarielinus.notes.ui.theme.secondaryPadding
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDateTime

@Composable
internal fun NotesScreen(
    modifier: Modifier = Modifier,
    search: String,
    notes: ImmutableList<NoteDomain>,
    selected: Int,
    screen: NoteScreen,
    editNote: (NoteId?) -> Unit,
    performAction: (NoteAction?) -> Unit,
    openDrawer: () -> Unit,
    onSearch: (String) -> Unit,
    onSelectedChanged: (NoteId) -> Unit,
    cancelSelection: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            var showDialog by remember {
                mutableStateOf<NoteAction?>(null)
            }

            if (selected > 0) {
                NotesInSelectionAppBar(
                    selected = selected,
                    noteCount = notes.size,
                    screen = screen,
                    cancelSelection = cancelSelection,
                    performAction = {
                        if (screen == NoteScreen.Notes || it == NoteAction.Trash) {
                            showDialog = it
                        } else performAction(it)
                    }
                )
            } else NotesAppBar(
                search = search,
                onSearch = onSearch,
                openDrawer = openDrawer,
                modifier = Modifier.fillMaxWidth()
            )

            showDialog?.let {
                NoteActionDialog(
                    text = stringResource(
                        R.string.notes_confirmation,
                        it.name.lowercase()
                    ),
                    confirmAction = {
                        performAction(it)
                    },
                    dismissDialog = { showDialog = null })
            }
        },
        content = {
            LazyColumn(
                modifier = Modifier.padding(it),
                contentPadding = PaddingValues(padding()),
                content = {
                    items(notes){note ->
                        NotesItem(
                            domain = note,
                            inSelection = selected > 0,
                            editNote = editNote,
                            selectedChanged = onSelectedChanged,
                        )
                        Spacer(modifier = Modifier.height(secondaryPadding()))
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {editNote(null)},
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Outlined.Add),
                    contentDescription = null
                )
            }
        }
    )
}

//NotesScreen Previews
@Preview
@Composable
private fun EmptyNotesScreenPreview() = NotesTheme {
    NotesScreen(
        notes = persistentListOf(),
        search = "",
        selected = 0,
        screen = NoteScreen.Notes,
        editNote = {},
        performAction = {},
        onSearch = {},
        onSelectedChanged = {},
        cancelSelection = {},
        openDrawer = {},
    )
}

@Preview
@Composable
private fun NotesScreenPreview() = NotesTheme {
    NotesScreen(
        notes = (1..10).map {
            NoteDomain(
                note = Note(
                    id = NoteId(it.toLong()),
                    title = "Title $it",
                    content = "Content $it",
                    dateCreated = LocalDateTime.now(),
                    action = null,
                    color = Note.noteColors[it]
                ),
                selected = it % 2 == 0,
            )
        }.toImmutableList(),
        search = "My Note",
        selected = 1,
        screen = NoteScreen.Notes,
        editNote = {},
        performAction = {},
        onSearch = {},
        onSelectedChanged = { },
        cancelSelection = {},
        openDrawer = {},
    )
}