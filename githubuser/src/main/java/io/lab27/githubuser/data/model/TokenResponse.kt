package io.lab27.githubuser.data.model

data class TokenResponse(
    val access_token: String = "",
    val expires_in: Int = 0,
    val refresh_token: String = "",
    val scope: String = "",
    val token_type: String = ""
)

data class FetchMeResponse(
    val id: Int = 0,
    val username: String = ""
)