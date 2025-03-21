package org.example.expenseservice.Data

data class JsonResponse(
    val success: Boolean,
    val message: String,
    val data: Any? = null
)
