package com.kiarielinus.notes.edit_note.presentation

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kiarielinus.notes.edit_note.R
import com.kiarielinus.notes.notes_api.Note
import com.kiarielinus.notes.notes_api.NoteAction
import com.kiarielinus.notes.ui.theme.NotesTheme
import com.kiarielinus.notes.ui.theme.TextColor
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
internal fun EditNoteScreen(
    title: String,
    content: String,
    editedAt: LocalDateTime,
    color: Int,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    performAction: (NoteAction?) -> Unit,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onColorChange: (Int) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val coroutine = rememberCoroutineScope()
    val actionMessage = stringResource(R.string.edit_note_action_performed)
    val undoMessage = stringResource(R.string.edit_note_undo)
    var showColors by remember { mutableStateOf(false) }
    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(color)
        )
    }

    LaunchedEffect(key1 = color, block = {
        noteBackgroundAnimatable.animateTo(
            targetValue = Color(color),
            animationSpec = tween(
                durationMillis = 500
            )
        )
    })

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            EditNoteAppBar(
                modifier = Modifier.fillMaxWidth(),
                navigateBack = navigateBack,
                performAction = {
                    coroutine.launch {
                        val result = scaffoldState.snackbarHostState.showSnackbar(
                            message = "$actionMessage ${it}d",
                            actionLabel = undoMessage
                        )
                        when (result) {
                            SnackbarResult.Dismissed -> {}
                            SnackbarResult.ActionPerformed -> {
                                performAction(null)
                            }
                        }
                    }
                    performAction(it)
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier.background(noteBackgroundAnimatable.value),
                content = {

                    if (showColors) LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(Note.noteColors) { colorInt ->
                            val currentColor = Color(colorInt)
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .shadow(15.dp, CircleShape)
                                    .clip(CircleShape)
                                    .background(currentColor)
                                    .border(
                                        width = 3.dp,
                                        color = if (color == colorInt) {
                                            Color.Black
                                        } else Color.Transparent,
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        onColorChange(colorInt)
                                        showColors = false
                                    }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = title,
                        onValueChange = onTitleChanged,
                        textStyle = MaterialTheme.typography.h6,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            textColor = Color.Black
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(R.string.edit_note_title),
                                style = MaterialTheme.typography.h6
                            )
                        }
                    )
                    Divider(color = TextColor)
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        value = content,
                        onValueChange = onContentChanged,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            textColor = Color.Black
                        ),
                        placeholder = {
                            Text(text = stringResource(R.string.edit_note_note))
                        }
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showColors = !showColors },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Filled.Palette),
                    contentDescription = null
                )
            }
        },
        bottomBar = {
            EditNoteBottomBar(
                modifier = Modifier.fillMaxWidth(),
                editedAt = editedAt,
            )
        }
    )
}

@Preview
@Composable
private fun EditNoteScreenPreview() = NotesTheme(true) {
    EditNoteScreen(
        title = "Stub note",
        content = "111111111111111",
        editedAt = LocalDateTime.now(),
        color = Note.noteColors[6],
        navigateBack = {},
        onContentChanged = {},
        onTitleChanged = {},
        performAction = {},
        onColorChange = {}
    )
}