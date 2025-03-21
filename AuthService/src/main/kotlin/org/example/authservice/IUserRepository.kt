package org.example.authservice

interface IUserRepository {
    fun findByUsername(username: String): User?
    fun save (user: User) : User?
}