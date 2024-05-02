package com.example.todoapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.data.models.ToDoTask

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDataBase : RoomDatabase() {

    abstract fun todoDao(): ToDoDao
}