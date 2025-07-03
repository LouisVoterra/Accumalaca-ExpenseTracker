package com.accumalaca.expensestracking.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg budgets :Budget)

    @Query("SELECT * FROM budget WHERE username = :user") //gant ini ya
    suspend fun selectAllBudget(user: String): List<Budget>

    @Query("SELECT * FROM budget WHERE uuid = :id")
    suspend fun selectBudget(id: Int): Budget

    @Query("UPDATE budget SET budgetName = :name, nominals = :nominal WHERE uuid = :id")
    suspend fun updateBudget(name: String, nominal: Int, id: Int)

    @Delete
    suspend fun deleteBudget(budget: Budget)

}