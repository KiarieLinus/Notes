package com.kiarielinus.notes.settings.data.model

import com.kiarielinus.notes.settings_api.Theme
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
internal value class ThemeEntity (val theme: Theme)

internal fun ThemeEntity?.toTheme() = this?.theme ?: Theme.System

internal fun Theme.toEntity() = ThemeEntity(this)