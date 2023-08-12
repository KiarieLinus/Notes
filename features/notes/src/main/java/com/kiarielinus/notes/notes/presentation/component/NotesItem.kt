package com.kiarielinus.notes.notes.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.core.graphics.ColorUtils
import com.kiarielinus.notes.model.formatted
import com.kiarielinus.notes.notes_api.Note
import com.kiarielinus.notes.notes_api.NoteId
import com.kiarielinus.notes.notes.domain.NoteDomain
import com.kiarielinus.notes.ui.theme.NotesTheme
import com.kiarielinus.notes.ui.theme.TextColor
import com.kiarielinus.notes.ui.theme.cornerRadius
import com.kiarielinus.notes.ui.theme.cutCornerSize
import java.time.LocalDateTime

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
internal fun NotesItem(
    modifier: Modifier = Modifier,
    domain: NoteDomain,
    inSelection: Boolean,
    cornerRadius: Dp = cornerRadius(),
    cutCornerSize: Dp = cutCornerSize(),
    editNote: (NoteId) -> Unit,
    selectedChanged: (NoteId) -> Unit
) = with(domain) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.combinedClickable(
            onClick = {
                if (inSelection || selected) {
                    selectedChanged(note.id)
                } else editNote(note.id)
            },
            onLongClick = {
                selectedChanged(note.id)
            }
        )
    ) {
        if (inSelection)
            Checkbox(
                checked = selected,
                onCheckedChange =  null,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.primary
                )
            )

        Box {
            Canvas(
                modifier = Modifier.matchParentSize()
            ) {
                val clipPath = Path().apply {
                    lineTo(size.width - cutCornerSize.toPx(), 0F)
                    lineTo(size.width, cutCornerSize.toPx())
                    lineTo(size.width, size.height)
                    lineTo(0F, size.height)
                    close()
                }

                clipPath(clipPath) {
                    drawRoundRect(
                        color = Color(note.color),
                        size = size,
                        cornerRadius = CornerRadius(cornerRadius.toPx())
                    )
                    drawRoundRect(
                        color = Color(
                            ColorUtils.blendARGB(note.color, android.graphics.Color.BLACK, 0.2F)
                        ),
                        topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                        size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                        cornerRadius = CornerRadius(cornerRadius.toPx())
                    )
                }
            }
            ListItem(
                modifier = Modifier.fillMaxWidth(),
                singleLineSecondaryText = true,
                overlineText = {
                    Text(note.dateModified.formatted)
                },
                text = {
                    Text(
                        text = note.title,
                        style = LocalTextStyle.current.copy(
                            fontWeight = FontWeight.W500,
                        ),
                        maxLines = 1,
                    )
                },
                secondaryText = if (note.content.isNotBlank()) {
                    {
                        Text(
                            text = note.content,
                            color = TextColor,
                            maxLines = 1,
                        )
                    }
                } else null,
            )
        }
    }
}

@Preview
@Composable
private fun NotePreview() = NotesTheme {
    NotesItem(
        domain = NoteDomain(
            note = Note(
                id = NoteId(1L),
                title = "Title goes Here",
                content = "Content goes here",
                dateCreated = LocalDateTime.now(),
                action = null,
            ),
            selected = false,
        ),
        inSelection = false,
        editNote = {},
        selectedChanged = {}
    )
}

@Preview
@Composable
private fun NoteSelectedPreview() = NotesTheme {
    NotesItem(
        domain = NoteDomain(
            note = Note(
                id = NoteId(1L),
                title = "Title goes Here",
                content = "Content goes here",
                dateCreated = LocalDateTime.now(),
                action = null,
            ),
            selected = true,
        ),
        inSelection = true,
        editNote = {},
        selectedChanged = {}
    )
}