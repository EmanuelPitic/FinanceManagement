package org.example.authservice

import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.util.Base64

@Service
class SHA256PasswordHasher: IPasswordHasher {
    override fun hash(username: String, password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val bytes = md.digest((username + password).toByteArray())
        return Base64.getEncoder().encodeToString(bytes)
    }
}