package io.lab27.githubuser.data.model

import io.lab27.githubuser.data.dao.User

sealed class UserModel {
    data class UserItem(val user: User) : UserModel()
    data class SeparatorItem(val description: String) : UserModel()
}