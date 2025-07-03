package com.accumalaca.expensestracking.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.accumalaca.expensestracking.model.Budget
import com.accumalaca.expensestracking.model.BudgetDatabase
import com.accumalaca.expensestracking.model.BudgetReport
import kotlinx.coroutines.launch

class ReportViewModel(application: Application) : AndroidViewModel(application) {
    private val budgetDao = BudgetDatabase(application).budgetDao()
    private val expenseDao = BudgetDatabase(application).expenseDao()

    val reportData = MutableLiveData<List<BudgetReport>>()
    val totalBudget = MutableLiveData<Int>()
    val totalSpent = MutableLiveData<Int>()

    fun loadReport(username: String) {
        viewModelScope.launch {
            val budgets = budgetDao.selectAllBudget(username)
            val expenses = expenseDao.getAllExpenseByUsername(username)

            val list = budgets.map { budget: Budget ->
                val spent = expenses.filter { it.category == budget.budgetName }.sumOf { it.amount }
                val remaining = budget.nominal - spent
                val percent = (spent.toFloat() / budget.nominal * 100).toInt()
                BudgetReport(budget.budgetName, spent, budget.nominal, remaining, percent)
            }

            reportData.postValue(list)
            totalBudget.postValue(budgets.sumOf { it.nominal })
            totalSpent.postValue(expenses.sumOf { it.amount })
        }
    }
}
