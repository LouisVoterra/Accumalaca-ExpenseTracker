package com.accumalaca.expensestracking.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.accumalaca.expensestracking.util.DB_NAME





@Database(entities = arrayOf(Budget::class), version = 1)   //ngasi tau room tabel DB dgn versi 1
abstract class BudgetDatabase:RoomDatabase(){  //kan kita bikin class BudgetDatabase untuk room ya, otomatis hrs mewarisi RoomDatabase()
    abstract fun budgetDao(): BudgetDao //ngasi tau Room DAO apa yg hrs di link

    //kata gpt room itu hrs singleton, karena kalai bikin 2 instance dlm 1 apps bisa bikin boros memory, error concurrenct & data corruption makanya dikasi @Violate + synchronize
    companion object{ //static block ala kotlin
        @Volatile private var instance: BudgetDatabase ?= null  //jaga jaga supaya thread lain bisa lihat instance terbaru
        private val LOCK = Any() //object kunci agar synchronized aman di multi-thread

        fun buildDatabase(context:Context) =
            Room.databaseBuilder( //bikin instance DB
                context.applicationContext, //menghindari memory leak
                BudgetDatabase::class.java,
                DB_NAME).build() //nama sqlite di storage app

        operator fun invoke(context: Context){  //bikin class utk dipanggil
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