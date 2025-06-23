package com.accumalaca.expensestracking.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.accumalaca.expensestracking.model.Budget
import com.accumalaca.expensestracking.model.BudgetDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailBudgetViewModel(application: Application):AndroidViewModel(application),CoroutineScope {
    private val job = Job()

    fun addBudget(list:List<Budget>){
        launch {
            val db = BudgetDatabase.buildDatabase(
                getApplication()
            )

            db.budgetDao().insertAll(*list.toTypedArray())
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}