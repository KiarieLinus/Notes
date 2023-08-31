package com.kiarielinus.notes.login.di

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.kiarielinus.notes.login.R
import com.kiarielinus.notes.login.presentation.GoogleAuthClient
import io.github.aakira.napier.Napier
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import javax.inject.Inject

internal class GoogleAuthClientImpl @Inject constructor(
    private val context: Context,
    private val oneTapClient: SignInClient
) : GoogleAuthClient {

    override suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            Napier.e("Sign in result error", e)
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    override fun signInWithIntent(intent: Intent): AuthCredential {
        val credential = try {
            oneTapClient.getSignInCredentialFromIntent(intent)
        } catch (e: ApiException) {
            Napier.e("Get credentials error", e)
            throw e
        }

        val googleIdToken = credential.googleIdToken
        return GoogleAuthProvider.getCredential(googleIdToken, null)
    }

    override suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.login_google_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}