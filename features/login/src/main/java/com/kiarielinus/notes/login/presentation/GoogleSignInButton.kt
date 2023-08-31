package com.kiarielinus.notes.login.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Constraints
import com.kiarielinus.notes.login.R
import com.kiarielinus.notes.ui.AppButton
import com.kiarielinus.notes.ui.theme.secondaryPadding

@Composable
internal fun GoogleSignInButton(
    modifier: Modifier,
    loading: Boolean,
    onGoogleSignInClick: () -> Unit,
) {
    AppButton(
        modifier = modifier,
        onClick = {
            onGoogleSignInClick()
        },
        isLoading = loading,
        content = {
            Row(
                modifier = Modifier.requiredHeight(IntrinsicSize.Min),
                content = {
                    Image(
                        modifier = Modifier
                            .layout { measurable, constraints ->
                                if (constraints.maxHeight == Constraints.Infinity) {
                                    layout(0, 0) {}
                                } else {
                                    val placeable = measurable.measure(constraints)
                                    layout(placeable.width, placeable.height) {
                                        placeable.place(0, 0)
                                    }
                                }
                            },
                        painter = painterResource(id = R.drawable.login_ic_google),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(secondaryPadding() / 2))
                    Text(stringResource(R.string.login_google))
                }
            )
        })
}