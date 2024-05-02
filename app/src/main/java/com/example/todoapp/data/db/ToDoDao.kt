package com.example.todoapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.data.models.ToDoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Query("SELECT * FROM TODO_TABLE ORDER BY id ASC")
    fun getAllTask(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM TODO_TABLE WHERE id = :taskId")
    fun getSelectTask(taskId: Int): Flow<ToDoTask>

    @Insert(onConflict = IGNORE)
    suspend fun addTask(toDoTask: ToDoTask)

    @Update
    suspend fun updateTask(toDoTask: ToDoTask)

    @Delete
    suspend fun deleteTask(task: ToDoTask)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTask()

    @Query("SELECT * FROM todo_table WHERE title LIKE :query OR description LIKE :query")
    fun searchDataBase(query: String): Flow<List<ToDoTask>>

    @Query("SELECT * FROM TODO_TABLE ORDER BY CASE WHEN priority Like 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END ")
    fun sortByLowPriority(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM TODO_TABLE ORDER BY CASE WHEN priority Like 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END ")
    fun sortByHighPriority(): Flow<List<ToDoTask>>
}