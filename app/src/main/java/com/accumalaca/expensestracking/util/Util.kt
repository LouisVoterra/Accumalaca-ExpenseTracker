package com.accumalaca.expensestracking.util

import android.content.Context
import com.accumalaca.expensestracking.model.BudgetDatabase

val DB_NAME = "budgetdb"

fun buildDB(context: Context): BudgetDatabase {
    val db = BudgetDatabase.buildDatabase(context)
    return db
}