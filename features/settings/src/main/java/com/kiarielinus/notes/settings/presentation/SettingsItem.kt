package com.kiarielinus.notes.settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.kiarielinus.notes.settings.R
import com.kiarielinus.notes.settings.api.SettingsToggle
import com.kiarielinus.notes.settings.api.Theme
import com.kiarielinus.notes.ui.theme.NotesTheme

@Composable
internal fun SettingsToggleItem(
    toggle: SettingsToggle,
    checked: Boolean,
    modifier: Modifier = Modifier,
    onToggle: () -> Unit,
) {
    SettingsItem(
        modifier = modifier,
        title = toggle.text,
        trailing = {
            Switch(
                checked = checked,
                onCheckedChange = {
                    onToggle()
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colors.primary,
                    checkedTrackColor = MaterialTheme.colors.primary
                )
            )
        },
        onClick = onToggle
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun SettingsThemeItem(
    theme: Theme,
    modifier: Modifier = Modifier,
    onThemeChanged: (Theme) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    SettingsItem(
        modifier = modifier,
        title = "Theme",
        trailing = {
            Text(theme.name)
        },
        onClick = {
            showDialog = true
        }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = stringResource(R.string.settings_choose_theme),
                    style = MaterialTheme.typography.h5
                )
            },
            shape = MaterialTheme.shapes.large,
            text = {
                Column {
                    Theme.values().forEach {
                        ListItem(
                            modifier = Modifier.clickable {
                                onThemeChanged(it)
                                showDialog = false
                            },
                            icon = {
                                RadioButton(
                                    selected = it == theme,
                                    onClick = null
                                )
                            },
                            text = {
                                Text(it.name)
                            },
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showDialog = false },
                    content = {
                        Text(stringResource(R.string.settings_cancel))
                    })
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SettingsItem(
    modifier: Modifier = Modifier,
    title: String,
    trailing: @Composable () -> Unit,
    onClick: () -> Unit
) = ListItem(
    modifier = modifier.clickable { onClick() },
    singleLineSecondaryText = true,
    text = {
        Text(
            text = title,
            style = LocalTextStyle.current.copy(
                fontWeight = FontWeight.W500
            ),
            maxLines = 1
        )
    },
    trailing = trailing,
)

//Previews
@Preview
@Composable
private fun SettingsThemeItemPreview() = NotesTheme {
    SettingsThemeItem(theme = Theme.System, onThemeChanged = {})
}

@Preview
@Composable
private fun SettingsToggleItemCheckedPreview() = NotesTheme {
    SettingsToggleItem(
        toggle = SettingsToggle.AddNewNotesToBottom,
        checked = true,
        onToggle = {},
    )
}

@Preview
@Composable
private fun SettingsToggleItemUncheckedPreview() = NotesTheme {
    SettingsToggleItem(
        toggle = SettingsToggle.AddNewNotesToBottom,
        checked = false,
        onToggle = {},
    )
}