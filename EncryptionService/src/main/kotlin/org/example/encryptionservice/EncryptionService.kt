package org.example.encryptionservice

import org.springframework.stereotype.Service

@Service
class EncryptionService(private val encryptor: Encryptor) {
    fun encryptSensitiveData(data: String): String = encryptor.encrypt(data)
    fun decryptSensitiveData(data: String): String = encryptor.decrypt(data)
}
