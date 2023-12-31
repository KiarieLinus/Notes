package com.kiarielinus.notes.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiarielinus.notes.settings_api.SettingsRepository
import com.kiarielinus.notes.settings_api.SettingsToggle
import com.kiarielinus.notes.settings_api.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    val theme = repository.theme.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = Theme.System
    )

    val toggles = repository.toggles.map {
        it.toImmutableMap()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = persistentMapOf()
    )

    fun updateTheme(theme: Theme) {
        viewModelScope.launch {
            repository.setTheme(theme)
        }
    }

    fun toggle(toggle: SettingsToggle) {
        viewModelScope.launch {
            repository.toggle(toggle)
        }
    }
}