package com.kiarielinus.notes.auth_impl.di

import com.kiarielinus.notes.auth_impl.api.AuthRepository
import com.kiarielinus.notes.auth_impl.data.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun repository(repository: AuthRepositoryImpl): AuthRepository
}