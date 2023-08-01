package com.kiarielinus.notes.notes.domain

import com.kiarielinus.notes.notes.api.Note

internal data class NoteDomain(
    val note: Note,
    val selected: Boolean,
)