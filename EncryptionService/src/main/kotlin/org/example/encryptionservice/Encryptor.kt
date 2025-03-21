package org.example.encryptionservice

interface Encryptor {
    fun encrypt(data: String): String
    fun decrypt(data: String): String
}