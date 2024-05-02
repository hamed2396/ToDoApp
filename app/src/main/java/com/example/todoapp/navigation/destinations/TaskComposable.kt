package com.example.todoapp.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todoapp.ui.screens.task.AddTaskScreen
import com.example.todoapp.utils.Action
import com.example.todoapp.utils.Constants.TASK_ARGUMENT_KEY
import com.example.todoapp.utils.Constants.TASK_SCREEN
import com.example.todoapp.utils.logError
import com.example.todoapp.viewmodel.ToDoViewModel

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit, viewModel: ToDoViewModel
) {
    composable(TASK_SCREEN, arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
        type = NavType.IntType
    })) { navBackStackEntry ->

        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        LaunchedEffect(key1 = taskId) {
            viewModel.getSelectedTask(taskId)
        }
        val selectedTask by viewModel.selectedTask.collectAsState()

        LaunchedEffect(key1 = selectedTask) {
            if (selectedTask != null || taskId == -1)
                viewModel.updateTaskFields(selectedTask)


        }

        AddTaskScreen(navigateToListScreen, selectedTask, viewModel)


    }


}
