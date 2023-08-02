package com.kiarielinus.notes.notes.api

import java.awt.Color
import java.time.LocalDateTime

data class Note(
    val id: NoteId,
    val title: String,
    val content: String,
    val dateCreated: LocalDateTime,
    val dateModified: LocalDateTime = dateCreated,
    val color: Int = noteColors.last(),
    val action: NoteAction?,
) {
    companion object{
        val noteColors = (0 .. 330 step 30).map{hue ->
            Color.HSBtoRGB(hue/360f,0.43f,1f)
        } + Color.HSBtoRGB(50f/360,0.43f,1f)
    }
}