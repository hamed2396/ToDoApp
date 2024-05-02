package com.example.todoapp.data.repository

import com.example.todoapp.data.db.ToDoDao
import com.example.todoapp.data.models.ToDoTask
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(private val dao: ToDoDao) {

    val getAllTask = dao.getAllTask()
    val sortByLowPriority = dao.sortByLowPriority()
    val sortByHighPriority = dao.sortByHighPriority()

    fun getSelectedTask(taskId: Int) = dao.getSelectTask(taskId)

    suspend fun addTask(toDoTask: ToDoTask) = dao.addTask(toDoTask)

    suspend fun updateTask(toDoTask: ToDoTask) = dao.updateTask(toDoTask)

    suspend fun deleteTask(toDoTask: ToDoTask) = dao.deleteTask(toDoTask)

    suspend fun deleteAllTask() = dao.deleteAllTask()

    fun searchDataBase(query: String) = dao.searchDataBase(query)

}