package org.example.expenseservice.Data

import java.util.Date

data class Expense(
    val id: Int? = null,
    val userId: Int? = null,
    val category: String? = null,
    val amount: Double? = null,
    val description: String? = null,
    val expenseDate: Date? = null
)

data class ExpenseRequest(
    val userId: Int,
    val category: String,
    val amount: Double,
    val description: String? = null,
    val expenseDate: Date
)

data class ExpenseUpdateRequest(
    val category: String? = null,
    val amount: Double? = null,
    val description: String? = null,
    val expenseDate: Date? = null
)
