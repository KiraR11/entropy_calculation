package com.example.entropy_calculation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data")
data class Data(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo(name = "message")
    var message:String,
    @ColumnInfo(name = "uncondEntropy")
    var uncondEntropy:Double,
    @ColumnInfo(name = "maxEntropy")
    var maxEntropy:Double,
    @ColumnInfo(name = "alphOver")
    var alphOver:Double,
    @ColumnInfo(name = "firstEntropy")
    var firstEntropy:Double,
):java.io.Serializable
