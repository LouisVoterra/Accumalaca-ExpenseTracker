package com.accumalaca.expensestracking.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.locks.Lock

class BudgetDatabase {
    @Database(entities = arrayOf(Budget::class), version = 1)
    abstract class BudgetDatabase:RoomDatabase(){
        abstract fun budgetDao(): BudgetDao

        companion object{
            @Volatile private var instance: BudgetDatabase ?= null
            private val LOCK = Any()

            fun buildDatabase(context:Context) =
                Room.databaseBuilder(
                    context.applicationContext,
                    BudgetDatabase::class.java,
                    "budgetdb").build()

            operator fun invoke(context: Context){
                if(instance == null){
                    synchronized(LOCK){
                        instance ?: buildDatabase(context).also{
                            instance = it
                        }
                    }
                }
            }
        }
    }
}