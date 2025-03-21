package org.example.encryptionservice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
// am implementat pentru testing
@RestController
@RequestMapping("/encryption")
class EncryptionController(private val encryptionService: EncryptionService) {

    @PostMapping("/encrypt")
    fun encryptEndpoint(@RequestBody request: EncryptRequest): ResponseEntity<EncryptResponse> {
        val encryptedData = encryptionService.encryptSensitiveData(request.data)
        return ResponseEntity.ok(EncryptResponse(encryptedData))
    }

    @PostMapping("/decrypt")
    fun decryptEndpoint(@RequestBody request: EncryptRequest): ResponseEntity<EncryptResponse> {
        val decryptedData = encryptionService.decryptSensitiveData(request.data)
        return ResponseEntity.ok(EncryptResponse(decryptedData))
    }
}


data class EncryptRequest(val data: String)
data class EncryptResponse(val data: String)