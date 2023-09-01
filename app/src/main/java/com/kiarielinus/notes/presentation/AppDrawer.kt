package com.kiarielinus.notes.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kiarielinus.notes.ui.theme.padding
import com.kiarielinus.notes.ui.theme.secondaryPadding
import com.kiarielinus.notes.R

enum class DrawerItem(val icon: ImageVector) {
    Notes(Icons.Outlined.Notes),
    Archive(Icons.Outlined.Archive),
    Trash(Icons.Outlined.Delete),
    Settings(Icons.Outlined.Settings),
    Logout(Icons.Outlined.Logout)
}

@Composable
internal fun AppDrawer(
    selected: DrawerItem,
    itemSelected: (DrawerItem) -> Unit,
) {
    Column {
        Text(
            modifier = Modifier
                .padding(vertical = secondaryPadding())
                .padding(start = padding()),
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h5,
        )
        DrawerItem.values().forEach { item ->
            DrawerItem(
                item = item,
                selected = item == selected,
                onClick = {
                    itemSelected(item)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DrawerItem(
    item: DrawerItem,
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit
) {
    ListItem(
        modifier = modifier
            .padding(padding())
            .clip(RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp))
            .background(
                color = if (selected) {
                    MaterialTheme.colors.onBackground.copy(alpha = 0.1f)
                } else Color.Unspecified,
            )
            .clickable { onClick() },
        icon = {
            Icon(
                item.icon, null,
                tint = MaterialTheme.colors.onBackground,
            )
        },
        text = {
            Text(
                text = item.name,
                color = MaterialTheme.colors.onBackground
            )
        }
    )
}
