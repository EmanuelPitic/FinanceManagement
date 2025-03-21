package org.example.expenseservice.Controller

import org.example.expenseservice.Data.ExpenseRequest
import org.example.expenseservice.Data.ExpenseUpdateRequest
import org.example.expenseservice.Data.JsonResponse
import org.example.expenseservice.ExpenseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/expenses")
class ExpenseController(
    private val expenseService: ExpenseService
) {

    @PostMapping
    fun addExpense(@RequestBody expenseRequest: ExpenseRequest): ResponseEntity<JsonResponse> {
        return try {
            val expense = expenseService.addExpense(expenseRequest)
            ResponseEntity.ok(JsonResponse(success = true, message = "Expense added successfully", data = expense))
        } catch (ex: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(JsonResponse(success = false, message = ex.message ?: "Error adding expense", data = null))
        }
    }

    @PutMapping("/{id}")
    fun updateExpense(
        @PathVariable("id") expenseId: Int,
        @RequestBody expenseUpdateRequest: ExpenseUpdateRequest
    ): ResponseEntity<JsonResponse> {
        return try {
            val expense = expenseService.updateExpense(expenseId, expenseUpdateRequest)
            ResponseEntity.ok(JsonResponse(success = true, message = "Expense updated successfully", data = expense))
        } catch (ex: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(JsonResponse(success = false, message = ex.message ?: "Error updating expense", data = null))
        }
    }

    @GetMapping("/{id}")
    fun getExpense(@PathVariable("id") expenseId: Int): ResponseEntity<JsonResponse> {
        return try {
            val expense = expenseService.getExpense(expenseId)
            ResponseEntity.ok(JsonResponse(success = true, message = "Expense retrieved successfully", data = expense))
        } catch (ex: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(JsonResponse(success = false, message = ex.message ?: "Expense not found", data = null))
        }
    }

    @GetMapping("/user/{userId}")
    fun getExpenses(@PathVariable("userId") userId: Int): ResponseEntity<JsonResponse> {
        return try {
            val expenses = expenseService.getExpenses(userId)
            ResponseEntity.ok(JsonResponse(success = true, message = "Expenses retrieved successfully", data = expenses))
        } catch (ex: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(JsonResponse(success = false, message = ex.message ?: "Error retrieving expenses", data = null))
        }
    }

    @DeleteMapping("/{id}")
    fun deleteExpense(@PathVariable("id") expenseId: Int): ResponseEntity<JsonResponse> {
        return try {
            val success = expenseService.deleteExpense(expenseId)
            if (success) {
                ResponseEntity.ok(JsonResponse(success = true, message = "Expense deleted successfully", data = null))
            } else {
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse(success = false, message = "Expense not found", data = null))
            }
        } catch (ex: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(JsonResponse(success = false, message = ex.message ?: "Error deleting expense", data = null))
        }
    }
}
