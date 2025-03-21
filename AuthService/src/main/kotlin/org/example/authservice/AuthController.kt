package org.example.authservice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/signup")
    fun signupEndpoint(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<JsonResponse> {
        val jsonResponse = authService.signUp(
            username = signUpRequest.username,
            password = signUpRequest.password,
            firstName = signUpRequest.firstName,
            lastName = signUpRequest.lastName
        )
        return ResponseEntity.ok(jsonResponse)
    }

    @PostMapping("/login")
    fun loginEndpoint(@RequestBody loginRequest: LoginRequest): ResponseEntity<JsonResponse> {
        val jsonResponse = authService.login(
            username = loginRequest.username,
            password = loginRequest.password
        )
        return ResponseEntity.ok(jsonResponse)
    }
}

data class SignUpRequest(val username: String, val password: String, val firstName: String, val lastName: String)
data class LoginRequest(val username: String, val password: String)

data class JsonResponse(val success: Boolean, val message: String, val data: Any? = null)
