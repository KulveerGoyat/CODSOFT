package com.example.todo_list_app.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todo_list_app.model.CardData

@Dao
interface DAO {
    @Insert
   suspend fun insertTask(entity: Entity)

    @Update
    suspend  fun updateTask(entity: Entity)

    @Delete
    suspend  fun deleteTask(entity: Entity)

    @Query(" Delete from t0_do_list")
    suspend  fun deleteAll()

    @Query("Select * from t0_do_list")
    suspend   fun getTaskList(): List<CardData>

    @Query("DELETE FROM sqlite_sequence WHERE name='t0_do_list'")
    suspend fun resetAutoIncrement()
}
