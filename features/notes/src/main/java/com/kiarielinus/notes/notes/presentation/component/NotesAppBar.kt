package com.kiarielinus.notes.notes.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Restore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kiarielinus.notes.notes.R
import com.kiarielinus.notes.notes_api.NoteAction
import com.kiarielinus.notes.notes.domain.NoteScreen
import com.kiarielinus.notes.ui.theme.NotesTheme
import com.kiarielinus.notes.ui.theme.padding

@Composable
internal fun NotesAppBar(
    search: String,
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    openDrawer: () -> Unit,
){
    OutlinedTextField(
        modifier = modifier.padding(horizontal = padding()),
        value =search,
        onValueChange =onSearch,
        shape = MaterialTheme.shapes.large,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            backgroundColor = Color.Transparent,
        ),
        placeholder = {
            Text(text = stringResource(id = R.string.notes_search))
        },
        leadingIcon = {
            IconButton(
                onClick = openDrawer
            ) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
internal fun NotesInSelectionAppBar(
    selected: Int,
    noteCount: Int,
    screen: NoteScreen,
    modifier: Modifier = Modifier,
    cancelSelection: () -> Unit,
    performAction: (NoteAction?) -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = cancelSelection,
                content = {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = stringResource(R.string.notes_cancel_selection)
                    )
                }
            )
        },
        title = {
            Text(text = "$selected/$noteCount")
        },
        actions = {
            if(screen != NoteScreen.Notes){
                IconButton(onClick = {
                    performAction(null)
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Restore,
                        contentDescription = stringResource(R.string.notes_restore),
                    )
                }
            }

            NoteAction.values().forEach { action ->
                when{
                    action == NoteAction.Archive && screen == NoteScreen.Notes -> {
                        action.Button(performAction = performAction)
                    }
                    action == NoteAction.Trash && screen != NoteScreen.Archive -> {
                        action.Button(performAction = performAction)
                    }
                }
            }
        }
    )
}

@Composable
private fun NoteAction.Button(
    modifier: Modifier = Modifier,
    performAction: (NoteAction) -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = {performAction(this)}){
        Icon(
            imageVector = NoteScreen.valueOf(name).icon,
            contentDescription = stringResource(
                R.string.notes_perform_action,
                name
            ),
        )
    }
}

//NoteAppBar Previews

@Preview
@Composable
private fun NotesAppBarPreview() = NotesTheme {
    NotesAppBar(
        search = "",
        onSearch = {},
        openDrawer = {},
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun NotesInSelectionAppBarPreview() = NotesTheme {
    NotesInSelectionAppBar(
        selected = 1,
        noteCount = 18,
        screen = NoteScreen.Notes,
        modifier = Modifier.fillMaxWidth(),
        cancelSelection = { /*TODO*/ },
        performAction = { /*TODO*/ }
    )
}

@Preview
@Composable
private fun NotesInReverseSelectionAppBarPreview() = NotesTheme {
    NotesInSelectionAppBar(
        selected = 2,
        noteCount = 18,
        screen = NoteScreen.Trash,
        modifier = Modifier.fillMaxWidth(),
        cancelSelection = { /*TODO*/ },
        performAction = { /*TODO*/ }
    )
}