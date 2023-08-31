package com.kiarielinus.notes.login.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.kiarielinus.notes.login.presentation.GoogleAuthClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GoogleClientDi {

    @Provides
    @Singleton
    fun providesGoogleClient(@ApplicationContext context: Context): GoogleAuthClient =
        GoogleAuthClientImpl(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
}