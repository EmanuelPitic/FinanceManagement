package org.example.authservice
//data class sa stocam info de la user
data class User(
    val id: Int? = null,
    val username: String? = null,
    val passwordHash: String? = null,
    val encryptedFirstName: String? = null,
    val encryptedLastName: String? = null
)
