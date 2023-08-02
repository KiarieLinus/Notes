package com.kiarielinus.notes.ui

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NoteActionDialog(
    text: String,
    modifier: Modifier = Modifier,
    confirmActionColor: Color = MaterialTheme.colors.error,
    confirmAction: () -> Unit,
    dismissDialog: () -> Unit,
) = AlertDialog(
    modifier = modifier,
    onDismissRequest = dismissDialog,
    text = {
        Text(text = text)
    },
    confirmButton = {
        AppButton(
            onClick = {
                confirmAction()
                dismissDialog()
            },
            color = confirmActionColor,
            content = {
                Text(stringResource(R.string.ui_yes), color = Color.White)
            }
        )
    },
    dismissButton = {
        AppButton(
            onClick = dismissDialog,
            content = {
                Text(stringResource(R.string.ui_no))
            })
    }
)

@Preview
@Composable
fun DialogPrev() {
    NoteActionDialog(
        text = "Content",
        confirmAction = { /*TODO*/ },
        dismissDialog = {}
    )
}