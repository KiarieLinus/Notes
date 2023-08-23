package com.kiarielinus.notes.auth_impl.data.domain

import com.google.firebase.auth.FirebaseUser
import com.kiarielinus.notes.auth_impl.api.User
import com.kiarielinus.notes.auth_impl.api.UserId
import com.kiarielinus.notes.auth_impl.data.model.UserEntity

internal object DomainMapper {
    fun toUserEntity(user: FirebaseUser) = UserEntity(
        id = user.uid,
        name = user.displayName ?: "User",
        email = user.email ?: "user@note.com",
    )

    fun toUser(user: UserEntity) = User(
        id = UserId(user.id),
        name = user.name,
        email = user.email,
    )
}