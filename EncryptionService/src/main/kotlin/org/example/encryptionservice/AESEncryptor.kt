package org.example.encryptionservice

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import java.util.Base64

class AESEncryptor(secret: String) : Encryptor {

    //cheia secreta trebuie convertita in keySpec, conform configuratiei AES
    private val keySpec = SecretKeySpec(secret.toByteArray(Charsets.UTF_8), "AES") //specific de 16 bytes

    //functie de criptare
    override fun encrypt(data: String): String {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")//creaza un AES cypher
        cipher.init(Cipher.ENCRYPT_MODE, keySpec) //initializeaza modulul de criptare folosind cheia secreta

        //genereaza val finala pt rezultatul encriptiei
        val encryptedBytes = cipher.doFinal(data.toByteArray(Charsets.UTF_8))

        //il converteste in base64 pt transmitere sigura
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    override fun decrypt(data: String): String {
        //la fel ca mai sus
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
        val decodedBytes = Base64.getDecoder().decode(data)
        val decryptedBytes = cipher.doFinal(decodedBytes)
        return String(decryptedBytes, Charsets.UTF_8)
    }
}
