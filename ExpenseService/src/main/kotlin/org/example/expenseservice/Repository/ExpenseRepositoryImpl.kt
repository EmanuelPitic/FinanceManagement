package org.example.expenseservice.Interfaces

import org.example.expenseservice.Data.Expense
import org.springframework.stereotype.Repository
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import javax.sql.DataSource

@Repository
class ExpenseRepositoryImpl(
    private val dataSource: DataSource
) : IExpenseRepository {

    override fun save(expense: Expense): Expense {
        val sql = """
        INSERT INTO expenses (user_id, category, amount, description, expense_date)
        VALUES (?, ?, ?, ?, ?)
    """.trimIndent()
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS).use { statement ->
                // Ensure required fields are non-null; throw exception if null.
                statement.setInt(1, expense.userId ?: throw IllegalArgumentException("userId is required"))
                statement.setString(2, expense.category ?: throw IllegalArgumentException("category is required"))
                statement.setDouble(3, expense.amount ?: throw IllegalArgumentException("amount is required"))
                statement.setString(4, expense.description)

                val todayUtc = LocalDate.now(ZoneOffset.UTC)

                val sqlTimestamp = if (expense.expenseDate != null) {
                    val expenseInstant = expense.expenseDate.toInstant()
                    val expenseLocalDate = expenseInstant.atZone(ZoneOffset.UTC).toLocalDate()
                    val todayUtc = LocalDate.now(ZoneOffset.UTC)

                    if (expenseLocalDate.isEqual(todayUtc)) {
                        Timestamp.from(OffsetDateTime.now(ZoneOffset.UTC).toInstant())
                    } else {
                        Timestamp.from(expenseInstant)
                    }
                } else {
                    Timestamp.from(OffsetDateTime.now(ZoneOffset.UTC).toInstant())
                }

                statement.setTimestamp(5, sqlTimestamp)



                val affectedRows = statement.executeUpdate()
                if (affectedRows == 0) {
                    throw RuntimeException("Creating expense failed, no rows affected.")
                }
                statement.generatedKeys.use { generatedKeys ->
                    if (generatedKeys.next()) {
                        return expense.copy(id = generatedKeys.getInt(1))
                    } else {
                        throw RuntimeException("Creating expense failed, no ID obtained.")
                    }
                }
            }
        }
    }

    override fun update(expense: Expense): Expense {
        val expenseId = expense.id ?: throw IllegalArgumentException("Expense id is required for update")
        val sql = """
            UPDATE expenses
            SET category = ?, amount = ?, description = ?, expense_date = ?
            WHERE id = ?
        """.trimIndent()
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, expense.category ?: throw IllegalArgumentException("category is required"))
                statement.setDouble(2, expense.amount ?: throw IllegalArgumentException("amount is required"))
                statement.setString(3, expense.description) // optional
                statement.setTimestamp(4, Timestamp.from(expense.expenseDate!!.toInstant()))
                statement.setInt(5, expenseId)
                val affectedRows = statement.executeUpdate()
                if (affectedRows == 0) {
                    throw RuntimeException("Updating expense failed, no rows affected.")
                }
            }
        }
        return expense
    }

    override fun findById(expenseId: Int): Expense? {
        val sql = "SELECT * FROM expenses WHERE id = ?"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setInt(1, expenseId)
                statement.executeQuery().use { resultSet ->
                    if (resultSet.next()) {
                        return mapResultSetToExpense(resultSet)
                    }
                }
            }
        }
        return null
    }

    override fun findByUserId(userId: Int): List<Expense> {
        val expenses = mutableListOf<Expense>()
        val sql = "SELECT * FROM expenses WHERE user_id = ?"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setInt(1, userId)
                statement.executeQuery().use { resultSet ->
                    while (resultSet.next()) {
                        expenses.add(mapResultSetToExpense(resultSet))
                    }
                }
            }
        }
        return expenses
    }

    override fun deleteById(expenseId: Int): Boolean {
        val sql = "DELETE FROM expenses WHERE id = ?"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setInt(1, expenseId)
                val affectedRows = statement.executeUpdate()
                return affectedRows > 0
            }
        }
    }

    private fun mapResultSetToExpense(resultSet: ResultSet): Expense {
        return Expense(
            id = resultSet.getInt("id"),
            userId = resultSet.getInt("user_id"),
            category = resultSet.getString("category"),
            amount = resultSet.getDouble("amount"),
            description = resultSet.getString("description"),
            expenseDate = resultSet.getTimestamp("expense_date")?.toInstant()?.let {
                java.util.Date.from(it)            }

        )
    }
}
