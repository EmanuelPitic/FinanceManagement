package org.example.authservice

import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.ResultSet
import javax.sql.DataSource

@Repository
class UserRepositoryImpl(
    private val dataSource: DataSource
) : IUserRepository {

    override fun findByUsername(username: String): User? {
        val sql = "SELECT * FROM users WHERE username = ?"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setString(1, username)
                preparedStatement.executeQuery().use { resultSet ->
                    if (resultSet.next()) {
                        return mapResultSetToUser(resultSet)
                    }
                }
            }
        }
        return null
    }

    override fun save(user: User): User? {
        val sql = "INSERT INTO users (username, password_hash, encrypted_first_name, encrypted_last_name) VALUES (?, ?, ?, ?)"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS).use { preparedStatement ->
                preparedStatement.setString(1, user.username)
                preparedStatement.setString(2, user.passwordHash)
                preparedStatement.setString(3, user.encryptedFirstName)
                preparedStatement.setString(4, user.encryptedLastName)
                val affectedRows = preparedStatement.executeUpdate()
                if (affectedRows == 0) {
                    throw RuntimeException("Creating user failed, no rows affected.")
                }
                preparedStatement.generatedKeys.use { generatedKeys ->
                    if (generatedKeys.next()) {
                        return user.copy(id = generatedKeys.getInt(1))
                    } else {
                        throw RuntimeException("Creating user failed, no ID obtained.")
                    }
                }
            }
        }
    }

    private fun mapResultSetToUser(resultSet: ResultSet): User {
        return User(
            id = resultSet.getInt("id"),
            username = resultSet.getString("username"),
            passwordHash = resultSet.getString("password_hash"),
            encryptedFirstName = resultSet.getString("encrypted_first_name"),
            encryptedLastName = resultSet.getString("encrypted_last_name")
        )
    }
}
