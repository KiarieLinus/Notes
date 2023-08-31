package com.kiarielinus.notes.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kiarielinus.notes.login.R
import com.kiarielinus.notes.ui.AppButton
import com.kiarielinus.notes.ui.ErrorText
import com.kiarielinus.notes.ui.input.Input
import com.kiarielinus.notes.ui.input.InputState
import com.kiarielinus.notes.ui.input.PasswordInput
import com.kiarielinus.notes.ui.theme.padding
import com.kiarielinus.notes.ui.theme.secondaryPadding

@Composable
internal fun LoginCard(
    state: LoginState,
    onLogin: () -> Unit,
    onGoogleSignInClick: () -> Unit,
) = with(state) {
    Column(
        modifier = Modifier
            .background(shape = MaterialTheme.shapes.medium, color = MaterialTheme.colors.surface)
            .padding(padding()),
        verticalArrangement = Arrangement.spacedBy(secondaryPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Text(
                text = stringResource(
                    if (isLogin) {
                        R.string.login_to_your_account
                    } else R.string.login_create_your_account
                ),
                style = MaterialTheme.typography.body1,
            )
            Input(
                title = "Email",
                state = InputState(
                    value = email,
                    onValueChanged = {
                        email = it
                    },
                    required = true,
                    error = emailError,
                ),
            )
            if (isLogin.not()) {
                Input(
                    title = stringResource(R.string.login_username),
                    state = InputState(
                        value = username,
                        onValueChanged = {
                            username = it
                        },
                        required = true,
                        error = usernameError
                    )
                )
            }
            PasswordInput(
                state = InputState(
                    value = password,
                    onValueChanged = {
                        password = it
                    },
                    required = true,
                    error = passwordError,
                )
            )
            AppButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    login(onLogin)
                },
                isLoading = isLoading,
                content = {
                    Text(
                        stringResource(
                            if (isLogin) {
                                R.string.login
                            } else R.string.login_register
                        )
                    )
                })
            Text(stringResource(R.string.login_or))
            GoogleSignInButton(
                modifier = Modifier.fillMaxWidth(),
                loading = isGoogleLoading,
                onGoogleSignInClick = onGoogleSignInClick,
            )
            loginError?.let { ErrorText(error = it) }
        }
    )
}