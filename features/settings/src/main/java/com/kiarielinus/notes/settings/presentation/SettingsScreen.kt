package com.kiarielinus.notes.settings.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kiarielinus.notes.settings_api.SettingsToggle
import com.kiarielinus.notes.settings_api.Theme
import com.kiarielinus.notes.ui.theme.NotesTheme
import com.kiarielinus.notes.ui.theme.secondaryPadding
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import kotlin.random.Random

@Composable
internal fun SettingsScreen(
    theme: Theme,
    toggles: ImmutableMap<SettingsToggle, Boolean>,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    themeChanged: (Theme) -> Unit,
    toggle: (SettingsToggle) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SettingsAppBar(
                modifier = modifier.fillMaxWidth(),
                navigateBack = navigateBack
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(secondaryPadding())
        ) {
            item {
                SettingsThemeItem(
                    modifier = Modifier.fillMaxWidth(),
                    theme = theme,
                    onThemeChanged = themeChanged,
                )
                Spacer(modifier = Modifier.height(secondaryPadding()))
            }
            toggles.entries.forEach { item ->
                item {
                    SettingsToggleItem(
                        modifier = Modifier.fillMaxWidth(),
                        toggle = item.key,
                        checked = item.value,
                        onToggle = { toggle(item.key) },
                    )
                    Spacer(modifier = Modifier.height(secondaryPadding()))
                }
            }
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() = NotesTheme {
    SettingsScreen(
        theme = Theme.System,
        toggles = buildMap {
            SettingsToggle.values().forEach {
                put(it, Random(Integer.MAX_VALUE).nextBoolean())
            }
        }.toImmutableMap(),
        navigateBack = {},
        themeChanged = {},
        toggle = {},
    )
}