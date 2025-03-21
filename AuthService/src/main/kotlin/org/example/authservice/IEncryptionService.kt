package org.example.authservice

interface IEncryptionService {
    fun encryptSensitiveData(data: String) :String
}