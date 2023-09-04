package com.kiarielinus.notes.edit_note.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kiarielinus.notes.notes_api.NoteId

@Composable
fun EditNoteRoute(
    id: NoteId?,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
) {
    val viewModel: EditNoteViewModel = viewModel()
    val goBack: () -> Unit = remember {
        {
            navigateBack()
        }
    }

    DisposableEffect(key1 = viewModel){
        //onStart
        viewModel.getNote(id)
        onDispose {
            //onStop
            viewModel.save()
        }
    }

    EditNoteScreen(
        modifier = modifier,
        title = viewModel.title,
        content = viewModel.content,
        editedAt = viewModel.editedAt,
        color = viewModel.color,
        navigateBack = goBack,
        onTitleChanged = viewModel::updateTitle,
        onContentChanged = viewModel::updateContent,
        performAction = { action ->
            viewModel.performAction(action)
            goBack()
        },
        onColorChange = viewModel::updateColor
    )
}