package com.example.todoapp.utils

import androidx.datastore.preferences.core.Preferences

object Constants {
    const val TODO_TABLE = "todo_table"
    const val TODO_DATABASE = "todo_database"

    const val LIST_SCREEN = "list/{action}"
    const val TASK_SCREEN = "task/{taskId}"
    const val LIST_ARGUMENT_KEY="action"
    const val TASK_ARGUMENT_KEY="taskId"
    const val MAX_TITLE_LENGTH=20
    const val PREFERENCES_NAME="preferences_name"
    const val PREFERENCES_KEY="preferences_key"
}