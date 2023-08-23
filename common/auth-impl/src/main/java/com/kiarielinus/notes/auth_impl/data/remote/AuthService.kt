package com.kiarielinus.notes.auth_impl.data.remote

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kiarielinus.notes.auth_impl.data.domain.DomainMapper
import com.kiarielinus.notes.auth_impl.data.model.UserEntity
import com.kiarielinus.notes.model.Resource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class AuthService @Inject constructor() {

    suspend fun login(
        email: String,
        password: String
    ): Resource<UserEntity> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            val user = auth.currentUser ?: throw Exception("User not found")
            Resource.Success(DomainMapper.toUserEntity(user))
        } catch (e: Exception) {
            if (e is CancellationException) throw Exception("Sign in Cancelled")
            Resource.Error(e.message ?: "Unknown Error")
        }
    }

    suspend fun loginWithCredential(
        credential: AuthCredential
    ): Resource<UserEntity> {
        return try {
            auth.signInWithCredential(credential).await()
            val user = auth.currentUser ?: throw Exception("Invalid credentials")
            Resource.Success(DomainMapper.toUserEntity(user))
        } catch (e: Exception) {
            if (e is CancellationException) throw Exception("Sign in Cancelled")
            Resource.Error(e.message ?: "Unknown Error")
        }
    }

    suspend fun register(
        email: String,
        password: String,
        userName: String = "",
    ): Resource<UserEntity> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val name = UserProfileChangeRequest.Builder().setDisplayName(userName).build()
            auth.currentUser?.updateProfile(name)?.await()
            val user = auth.currentUser ?: throw Exception("User not created")
            Resource.Success(DomainMapper.toUserEntity(user))
        } catch (e: Exception) {
            if (e is CancellationException) throw Exception("Sign in Cancelled")
            Resource.Error(e.message ?: "Unknown Error")
        }
    }

    fun logout() = auth.signOut()

    companion object {
        private val auth = Firebase.auth
    }
}