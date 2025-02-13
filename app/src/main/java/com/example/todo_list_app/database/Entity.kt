package com.example.todo_list_app.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "T0_DO_LIST")
@TypeConverters(Converters::class)
data class Entity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String,
    var priority: String,
    var status :String,
    var dueDate : Date?
    )
