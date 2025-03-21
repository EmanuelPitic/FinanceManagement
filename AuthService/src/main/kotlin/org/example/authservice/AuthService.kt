package org.example.authservice

import org.springframework.stereotype.Service

@Service
class AuthService(
    val encryptionService: IEncryptionService,
    val passwordHasher: IPasswordHasher,
    val userRepository: IUserRepository
) {
    fun signUp(username: String, password: String, firstName: String, lastName: String): JsonResponse {
        if (userRepository.findByUsername(username) != null) {
            return JsonResponse(success = false, message = "Username already exists")
        }
        val hashedPassword = passwordHasher.hash(username,password)

        val encryptedFirstName = encryptionService.encryptSensitiveData(firstName)
        val encryptedLastName = encryptionService.encryptSensitiveData(lastName)

        val newUser = User(
            username = username,
            passwordHash = hashedPassword,
            encryptedFirstName = encryptedFirstName,
            encryptedLastName = encryptedLastName
        )

        val savedUser = userRepository.save(newUser)
        return if (savedUser != null) {
            JsonResponse(success = true, message = "Signup successful", data = savedUser)
        } else {
            JsonResponse(success = false, message = "Signup failed")
        }

    }

    fun login(username: String, password: String) : JsonResponse{
        val user = userRepository.findByUsername(username)

        if (user == null) {
            return JsonResponse(success = false, message = "Invalid username or password")
        }

        val isValidPassword = passwordHasher.hash(username,password) == user.passwordHash

        return if (isValidPassword) {
            JsonResponse(success = true, message = "Login successful", data = user)
        } else {
            JsonResponse(success = false, message = "Invalid username or password")
        }
    }
}
