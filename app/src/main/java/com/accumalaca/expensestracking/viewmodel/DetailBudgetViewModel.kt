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

class DetailBudgetViewModel(application: Application):AndroidViewModel(application),CoroutineScope {
    private val job = Job()

    val budgetLD = MutableLiveData<Budget>()

    fun fetch(uuid: Int){
        launch {
            val db = buildDB(getApplication())

            budgetLD.postValue(db.budgetDao().selectBudget(uuid))
        }
    }

    fun addBudget(list:List<Budget>){
        launch {
//            val db = BudgetDatabase.buildDatabase(
//                getApplication()
//            )

            val db = buildDB(getApplication())

            db.budgetDao().insertAll(*list.toTypedArray())
        }
    }

    fun updateBudget(namaBudget: String, nominal: Int, uuid: Int){
        launch {
            val db = buildDB(getApplication())
            db.budgetDao().updateBudget(namaBudget,nominal,uuid)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}