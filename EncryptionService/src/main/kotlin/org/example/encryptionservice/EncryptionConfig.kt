package org.example.encryptionservice

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class EncryptionConfig {

    @Value("\${encryption.secret-key}")
    lateinit var secretKey: String

    @Bean
    fun encryptor(): Encryptor = AESEncryptor(secretKey)
}