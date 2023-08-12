package com.kiarielinus.notes.notes.domain

import androidx.compose.runtime.Stable
import com.kiarielinus.notes.notes_api.Note

@Stable
internal data class NoteDomain(
    val note: Note,
    val selected: Boolean,
)