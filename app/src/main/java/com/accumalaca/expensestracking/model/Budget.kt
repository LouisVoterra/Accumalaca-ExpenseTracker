package com.accumalaca.expensestracking.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//ini adalah data class yang akan digunakan untuk keperluan database, maka dari itu diperlukan class agar jadi database ngehe

@Entity("budget")
data class Budget(


    @ColumnInfo("budgetName")  //untuk var budgetName kita kasi nama budgetName sbg column di database
    var budgetName:String,
    @ColumnInfo("nominals") //untuk var nominals kita kasi nama nominals sbg column kedua di tabase
    var nominal:Int
){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0
}