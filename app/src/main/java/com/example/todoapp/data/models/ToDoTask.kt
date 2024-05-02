package com.example.todoapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.utils.Constants.TODO_TABLE

@Entity(tableName = TODO_TABLE)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority
)
