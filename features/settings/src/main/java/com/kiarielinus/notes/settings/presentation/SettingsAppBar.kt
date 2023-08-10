package com.kiarielinus.notes.settings.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kiarielinus.notes.settings.R
import com.kiarielinus.notes.ui.theme.NotesTheme

@Composable
internal fun SettingsAppBar(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = navigateBack
            ) {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.settings_go_back)
                )
            }
        },
        title = {
            Text(text = stringResource(R.string.settings))
        },
    )
}

@Preview
@Composable
private fun NotesAppBarPreview() = NotesTheme {
    SettingsAppBar(
        modifier = Modifier.fillMaxWidth(),
        navigateBack = {},
    )
}
