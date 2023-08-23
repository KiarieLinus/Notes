package com.kiarielinus.notes.login.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.firebase.auth.AuthCredential

@Composable
internal fun GoogleSignInButton(
    loading: Boolean,
    modifier: Modifier,
    onClick: () -> Unit,
    onError: (String) -> Unit,
    login: (AuthCredential) -> Unit
    ) {

}