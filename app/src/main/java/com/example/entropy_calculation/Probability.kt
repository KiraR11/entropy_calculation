package com.example.entropy_calculation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "charProb")
data class Probability(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo(name = "chars")
    var chars:String?,
    @ColumnInfo(name = "probabilChar")
    var probabilChar: Double?,
    @ColumnInfo(name = "bigram")
    var bigram:String?,
    @ColumnInfo(name = "probabilBigram")
    var probabilBigram: Double?,
    ):java.io.Serializable
