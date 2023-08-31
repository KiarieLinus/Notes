package com.kiarielinus.notes.login.presentation

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.kiarielinus.notes.login.R
import com.kiarielinus.notes.ui.theme.NotesTheme
import com.kiarielinus.notes.ui.theme.padding
import com.kiarielinus.notes.ui.theme.secondaryPadding
import kotlinx.coroutines.launch

@Composable
internal fun LoginScreen(
    state: LoginState,
    modifier: Modifier = Modifier,
    googleAuthClient: GoogleAuthClient? = null,
    onLogin: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                scope.launch {
                    val credential = googleAuthClient!!.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    state.googleLogin(credential,onLogin)
                }
            }
        }
    )


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = padding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = {
            Spacer(modifier = Modifier.height(padding() + secondaryPadding()))
            LoginCard(
                state = state,
                onLogin = onLogin,
                onGoogleSignInClick = {
                    scope.launch {
                        val signIntentSender = googleAuthClient!!.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(secondaryPadding()))
            BottomLoginText(
                isLogin = state.isLogin,
                onIsLoginChanged = { state.isLogin = it }
            )
        }
    )
}

@Composable
private fun BottomLoginText(
    isLogin: Boolean,
    onIsLoginChanged: (Boolean) -> Unit,
) = Text(
    modifier = Modifier.clickable {
        onIsLoginChanged(!isLogin)
    },
    text = buildAnnotatedString {
        withStyle(MaterialTheme.typography.body2.toSpanStyle()) {
            withStyle(style = SpanStyle(color = MaterialTheme.colors.onBackground)) {
                append(
                    stringResource(
                        if (isLogin) {
                            R.string.login_dont_have_account
                        } else R.string.login_already_have_account
                    )
                )
            }
            append(" ")
            withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                append(
                    stringResource(
                        if (isLogin) {
                            R.string.login_register
                        } else R.string.login
                    )
                )
            }
        }
    }
)

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() = NotesTheme {
    LoginScreen(
        state = LoginState(),
    )
}