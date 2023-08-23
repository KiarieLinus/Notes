package com.kiarielinus.notes.auth_impl.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class UserEntity(
    val id: String,
    val name: String,
    val email: String,
)