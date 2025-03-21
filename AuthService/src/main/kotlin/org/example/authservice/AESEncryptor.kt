package org.example.authservice
import org.json.JSONObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.stereotype.Service

@Service
class AESEncryptor : IEncryptionService {
    override fun encryptSensitiveData(data: String): String {
        //o sa luam rezultatul interogarii serviciului Encypt
        val client = OkHttpClient()
        val mediaType = "application/json".toMediaType()
        val jsonBody = "{\"data\":\"$data\"}"
        val body = jsonBody.toRequestBody(mediaType)
        val request = Request.Builder()
            .url("http://localhost:8082/encryption/encrypt")
            .post(body)
            .addHeader("Content-Type", "application/json")
            .build()
        val response = client.newCall(request).execute()
        val responseBody = JSONObject(response.body!!.string())
        return responseBody.getString("data")
    }
}