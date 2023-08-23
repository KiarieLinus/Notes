package com.kiarielinus.notes.auth_impl.data.repository

import com.google.firebase.auth.AuthCredential
import com.kiarielinus.notes.auth_impl.api.AuthRepository
import com.kiarielinus.notes.auth_impl.api.User
import com.kiarielinus.notes.auth_impl.data.domain.DomainMapper
import com.kiarielinus.notes.auth_impl.data.local.AuthDataStore
import com.kiarielinus.notes.auth_impl.data.model.UserEntity
import com.kiarielinus.notes.auth_impl.data.remote.AuthService
import com.kiarielinus.notes.model.Resource
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val remote: AuthService,
    private val local: AuthDataStore
) : AuthRepository {
    override val currentUser: Flow<User?>
        get() = local.get().map { user ->
            user?.let {
                DomainMapper.toUser(it)
            }
        }

    override suspend fun login(email: String, password: String): Resource<String> =
        handleResponse(remote.login(email, password))

    override suspend fun signInWithCredential(credential: Any): Resource<String> {
        return when (credential) {
            is AuthCredential -> handleResponse(remote.loginWithCredential(credential))
            else -> throw UnsupportedOperationException("Only Firebase AuthCredentials supported")
        }
    }

    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): Resource<String> = handleResponse(
        remote.register(email, password, username)
    )

    override suspend fun logout() {
        remote.logout()
        local.clear()
    }

    private suspend fun handleResponse(
        response: Resource<UserEntity>
    ): Resource<String> {
        return if (response is Resource.Success) {
            response.data?.let { local.save(it) }
            Napier.d("Saved ${response.data} to store")
            Resource.Success("Authentication successful")
        } else Resource.Error(response.message!!)
    }
}