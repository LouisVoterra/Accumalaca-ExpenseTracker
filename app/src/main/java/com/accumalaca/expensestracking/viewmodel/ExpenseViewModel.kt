package com.accumalaca.expensestracking.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.accumalaca.expensestracking.model.Expense
import com.accumalaca.expensestracking.util.buildDB
import com.accumalaca.expensestracking.util.SessionManager
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ExpenseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    private val username = SessionManager(getApplication()).getLoggedInUser()

    val selectedExpense = MutableLiveData<Expense>()
    val expenseLD = MutableLiveData<List<Expense>>()
    val loadingLD = MutableLiveData<Boolean>()
    val errorLD = MutableLiveData<Boolean>()

    fun refresh() {
        loadingLD.postValue(true)
        errorLD.postValue(false)

        launch {
            username?.let { user ->
                val db = buildDB(getApplication())
                val list = db.expenseDao().getAllExpenseByUsername(user)
                expenseLD.postValue(list)
                loadingLD.postValue(false)
            }
        }
    }

    fun addExpense(expense: Expense) {
        launch {
            username?.let {
                expense.username = it
                val db = buildDB(getApplication())
                db.expenseDao().insertAll(expense)
                refresh()
            }
        }
    }


    fun fetch(uuid: Int) {
        launch {
            val db = buildDB(getApplication())
            selectedExpense.postValue(db.expenseDao().getExpense(uuid))
        }
    }

    fun updateExpense(
        expenseName: String,
        amount: Int,
        category: String,
        date: String,
        uuid: Int
    ) {
        launch {
            val db = buildDB(getApplication())
            db.expenseDao().updateExpense(expenseName, amount, category, date, uuid)
            refresh()
        }
    }

    fun deleteExpense(expense: Expense) {
        launch {
            val db = buildDB(getApplication())
            db.expenseDao().deleteExpense(expense)
            refresh()
        }
    }
}


// ga jauh beda isinya sama model model sebelumnya
