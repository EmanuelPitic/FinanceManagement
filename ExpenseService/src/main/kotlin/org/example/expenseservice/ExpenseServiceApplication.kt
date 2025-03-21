package org.example.expenseservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExpenseServiceApplication

fun main(args: Array<String>) {
	runApplication<ExpenseServiceApplication>(*args)
}
