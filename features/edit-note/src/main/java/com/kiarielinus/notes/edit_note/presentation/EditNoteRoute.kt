package com.kiarielinus.notes.edit_note.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kiarielinus.notes.notes_api.NoteId
import com.kiarielinus.notes.ui.OnLifeCycleEvent

@Composable
fun EditNoteRoute(
    id: NoteId?,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
) {
    val viewModel: EditNoteViewModel = viewModel()
    val goBack: () -> Unit = remember {
        {
            viewModel.save()
            navigateBack()
        }
    }

    LaunchedEffect(key1 = id, block = {
        viewModel.getNote(id)
    })

    BackHandler {
        goBack()
    }

    OnLifeCycleEvent(event = Lifecycle.Event.ON_PAUSE) {
        viewModel.save()
    }

    EditNoteScreen(
        modifier = modifier,
        title = viewModel.title,
        content = viewModel.content,
        editedAt = viewModel.editedAt,
        navigateBack = goBack,
        onTitleChanged = viewModel::updateTitle,
        onContentChanged = viewModel::updateContent,
        performAction = { action ->
            viewModel.performAction(action)
            goBack()
        },
    )
}