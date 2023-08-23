package com.kiarielinus.notes.auth_impl.api

data class User(
    val id: UserId,
    val name: String,
    val email: String,
)