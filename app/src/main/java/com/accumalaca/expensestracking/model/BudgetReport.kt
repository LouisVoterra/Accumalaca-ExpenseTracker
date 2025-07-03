package com.accumalaca.expensestracking.model

data class BudgetReport(
    val name: String,
    val spent: Int,
    val max: Int,
    val remaining: Int,
    val percent: Int
)
