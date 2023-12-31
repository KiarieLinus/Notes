package com.kiarielinus.notes.notes.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.ui.graphics.vector.ImageVector

enum class NoteScreen(val icon: ImageVector) {
    Archive(Icons.Outlined.Archive),
    Notes(Icons.Outlined.Notes),
    Trash(Icons.Outlined.Delete),
}