package org.example.expenseservice.Interfaces

import org.example.expenseservice.Data.Expense

interface IExpenseRepository {
    fun save(expense: Expense): Expense
    fun update(expense: Expense): Expense
    fun findById(expenseId: Int): Expense?
    fun findByUserId(userId: Int): List<Expense>
    fun deleteById(expenseId: Int): Boolean
}
