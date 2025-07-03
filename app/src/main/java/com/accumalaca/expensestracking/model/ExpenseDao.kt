package com.accumalaca.expensestracking.model

import androidx.room.*

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expense WHERE username = :username ORDER BY date DESC")
    suspend fun getAllExpenseByUsername(username: String): List<Expense>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg expense: Expense)

    @Query("SELECT * FROM expense WHERE uuid = :uuid")
    suspend fun getExpense(uuid: Int): Expense

    @Query("""
    UPDATE expense 
    SET expenseName = :expenseName, 
        amount = :amount, 
        category = :category, 
        date = :date 
    WHERE uuid = :uuid
""")
    suspend fun updateExpense(
        expenseName: String,
        amount: Int,
        category: String,
        date: String,
        uuid: Int
    )

    @Delete
    suspend fun deleteExpense(expense: Expense)
}
