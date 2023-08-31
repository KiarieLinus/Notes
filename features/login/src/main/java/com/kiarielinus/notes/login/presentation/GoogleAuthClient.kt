package com.kiarielinus.notes.login.presentation

import android.content.Intent
import android.content.IntentSender
import com.google.firebase.auth.AuthCredential

interface GoogleAuthClient {
    suspend fun signIn(): IntentSender?

    fun signInWithIntent(intent: Intent): AuthCredential

    suspend fun signOut()
}