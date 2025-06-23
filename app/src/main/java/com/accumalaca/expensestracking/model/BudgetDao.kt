package com.accumalaca.expensestracking.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg budgets :Budget)

    @Query("SELECT * FROM budget")
    fun selectAllBudget(): List<Budget>

    @Query("SELECT * FROM budget WHERE uuid= :id")
    fun selectBudget(id: Int):Budget

    @Delete
    fun deleteBudget(budget: Budget)


}