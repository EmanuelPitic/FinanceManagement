package org.example.expenseservice

import org.example.expenseservice.Data.Expense
import org.example.expenseservice.Data.ExpenseRequest
import org.example.expenseservice.Data.ExpenseUpdateRequest
import org.example.expenseservice.Interfaces.IExpenseRepository
import org.springframework.stereotype.Service

@Service
class ExpenseService(
    private val expenseRepository: IExpenseRepository
) {

    fun addExpense(expenseRequest: ExpenseRequest): Expense {
        val expense = Expense(
            id = null,
            userId = expenseRequest.userId,
            category = expenseRequest.category,
            amount = expenseRequest.amount,
            description = expenseRequest.description,
            expenseDate = expenseRequest.expenseDate
        )
        return expenseRepository.save(expense)
    }

    fun updateExpense(expenseId: Int, expenseUpdateRequest: ExpenseUpdateRequest): Expense {
        val existingExpense = expenseRepository.findById(expenseId)
            ?: throw RuntimeException("Expense with id $expenseId not found")
        val updatedExpense = existingExpense.copy(
            category = expenseUpdateRequest.category ?: existingExpense.category,
            amount = expenseUpdateRequest.amount ?: existingExpense.amount,
            description = expenseUpdateRequest.description ?: existingExpense.description,
            expenseDate = expenseUpdateRequest.expenseDate ?: existingExpense.expenseDate
        )
        return expenseRepository.update(updatedExpense)
    }

    fun getExpense(expenseId: Int): Expense {
        return expenseRepository.findById(expenseId)
            ?: throw RuntimeException("Expense with id $expenseId not found")
    }


    fun getExpenses(userId: Int): List<Expense> {
        return expenseRepository.findByUserId(userId)
    }

    fun deleteExpense(expenseId: Int): Boolean {
        return expenseRepository.deleteById(expenseId)
    }
}
