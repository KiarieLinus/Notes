package com.kiarielinus.notes.auth_impl.api

import com.kiarielinus.notes.model.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>

    suspend fun login(email: String, password: String): Resource<String>

    suspend fun signInWithCredential(credential: Any) : Resource<String>

    suspend fun register(email: String,username: String, password: String): Resource<String>

    suspend fun logout()
}