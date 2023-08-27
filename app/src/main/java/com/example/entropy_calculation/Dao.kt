package com.example.entropy_calculation

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao {
    @Insert
    fun insertCharList(charList : List<Probability>)

    @Insert
    fun insertData(data : Data)

    @Query(value = "SELECT * FROM data")
    fun getData(): List<Data>

    @Query(value = "SELECT COUNT(1) FROM data")
    fun existenceCheck(): Int

    @Query("DELETE FROM data")
    fun deleteData()

    @Query(value = "SELECT * FROM charProb WHERE chars != :non")
    fun getCharList(non:String? = "NULL"): List<Probability>

    @Query(value = "SELECT * FROM charProb WHERE bigram != :non")
    fun getBigramList(non:String? = "NULL"): List<Probability>

    @Query("DELETE FROM charProb")
    fun deleteCharList()


}