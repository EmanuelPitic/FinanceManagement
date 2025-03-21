package org.example.authservice

interface IPasswordHasher {
    fun hash(username: String, password: String): String
}