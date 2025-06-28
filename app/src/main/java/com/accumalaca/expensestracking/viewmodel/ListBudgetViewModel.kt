package com.accumalaca.expensestracking.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.accumalaca.expensestracking.model.Budget
import com.accumalaca.expensestracking.model.BudgetDatabase
import com.accumalaca.expensestracking.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListBudgetViewModel(application: Application) :AndroidViewModel(application),CoroutineScope
{
    val budgetLD = MutableLiveData<List<Budget>>()
    val budgetLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh(){
        loadingLD.value = true
        budgetLoadErrorLD.value = true
        launch {
//            val db = BudgetDatabase.buildDatabase(
//                getApplication()
//            )

            val db = buildDB(getApplication())

            budgetLD.postValue(db.budgetDao().selectAllBudget())
            loadingLD.postValue(false)
        }
    }

    fun clearTask(budget: Budget) {
        launch {
            val db = BudgetDatabase.buildDatabase(
                getApplication()
            )

            db.budgetDao().deleteBudget(budget)
            budgetLD.postValue(db.budgetDao().selectAllBudget())
        }
    }


}