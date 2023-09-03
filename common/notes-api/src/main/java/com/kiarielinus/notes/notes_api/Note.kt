package com.kiarielinus.notes.notes_api

import java.time.LocalDateTime
import kotlin.math.floor

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
            convertHSBtoRGB(hue/360f)
        } + convertHSBtoRGB(50f/360)
    }
}

/**
 * --- sourced from java.awt.Color.HSBtoRGB ---
 * Converts the components of a color, as specified by the HSB
 * model, to an equivalent set of values for the default RGB model.
 */
private fun convertHSBtoRGB(
    hue: Float,
    saturation: Float = 0.43f,
    brightness: Float = 1f
): Int {
    var r = 0
    var g = 0
    var b = 0
    if (saturation == 0f) {
        b = (brightness * 255.0f + 0.5f).toInt()
        g = b
        r = g
    } else {
        val h = (hue - floor(hue.toDouble()).toFloat()) * 6.0f
        val f = h - floor(h.toDouble()).toFloat()
        val p = brightness * (1.0f - saturation)
        val q = brightness * (1.0f - saturation * f)
        val t = brightness * (1.0f - saturation * (1.0f - f))
        when (h.toInt()) {
            0 -> {
                r = (brightness * 255.0f + 0.5f).toInt()
                g = (t * 255.0f + 0.5f).toInt()
                b = (p * 255.0f + 0.5f).toInt()
            }

            1 -> {
                r = (q * 255.0f + 0.5f).toInt()
                g = (brightness * 255.0f + 0.5f).toInt()
                b = (p * 255.0f + 0.5f).toInt()
            }

            2 -> {
                r = (p * 255.0f + 0.5f).toInt()
                g = (brightness * 255.0f + 0.5f).toInt()
                b = (t * 255.0f + 0.5f).toInt()
            }

            3 -> {
                r = (p * 255.0f + 0.5f).toInt()
                g = (q * 255.0f + 0.5f).toInt()
                b = (brightness * 255.0f + 0.5f).toInt()
            }

            4 -> {
                r = (t * 255.0f + 0.5f).toInt()
                g = (p * 255.0f + 0.5f).toInt()
                b = (brightness * 255.0f + 0.5f).toInt()
            }

            5 -> {
                r = (brightness * 255.0f + 0.5f).toInt()
                g = (p * 255.0f + 0.5f).toInt()
                b = (q * 255.0f + 0.5f).toInt()
            }
        }
    }
    return -0x1000000 or (r shl 16) or (g shl 8) or (b shl 0)
}