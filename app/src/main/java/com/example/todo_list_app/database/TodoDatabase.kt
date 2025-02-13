package com.example.todo_list_app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Entity::class], version = 1)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun dao(): DAO
}