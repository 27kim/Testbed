package io.lab27.githubuser.network

import io.lab27.githubuser.data.dao.User

data class UserResponse(
        val items: List<User>
)