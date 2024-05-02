package com.example.todoapp.ui.screens.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import com.example.todoapp.R
import com.example.todoapp.utils.Action
import com.example.todoapp.viewmodel.ToDoViewModel
import kotlinx.coroutines.launch

@Composable
fun ListScreen(navigateToTaskScreen: (taskId: Int) -> Unit, viewModel: ToDoViewModel) {
    val searchAppBarState by viewModel.searchAppBarState
    val searchTextState by viewModel.searchTextState
    val action by viewModel.action

    val host = remember {
        SnackbarHostState()


    }

    LaunchedEffect(key1 = true) {
        viewModel.getAllTask()
        viewModel.readSortState()
    }
    ShowSnackBar(handleDataBaseAction = { viewModel.handleDataBaseActions(action) },
        hostState = host,
        taskTitle = viewModel.title.value,
        actions = action,
        onUndoClicked = { viewModel.action.value = it })


    val allTasks by viewModel.allTask.collectAsState()
    val searchTasks by viewModel.searchTask.collectAsState()
    val sortState by viewModel.sortState.collectAsState()
    val lowPriorityTask by viewModel.lowPriorityTask.collectAsState()
    val highPriorityTask by viewModel.highPriorityTask.collectAsState()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = host) },
        content = {

            ListContent(
                allTasks = allTasks,
                navigateToTaskScreen,
                it.calculateTopPadding(),
                searchAppBarState = searchAppBarState,
                searchedTask = searchTasks,
                lowPriorityTask = lowPriorityTask,
                highPriorityTask = highPriorityTask,
                sortState = sortState,
                onSwipeToDelete = { action, task ->
                    viewModel.action.value = action
                    viewModel.updateTaskFields(task)
                }
            )


        },
        floatingActionButton = { ListFab(navigateToTaskScreen) },
        topBar = {
            ListAppBar(
                viewModel, searchAppBarState, searchTextState
            )
        }

    )


}

@Composable
fun ShowSnackBar(
    handleDataBaseAction: () -> Unit,
    hostState: SnackbarHostState,
    taskTitle: String,
    actions: Action,
    onUndoClicked: (Action) -> Unit
) {
    handleDataBaseAction()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = actions) {
        if (actions != Action.NO_ACTION) {
            scope.launch {
                val result = hostState.showSnackbar(
                    message = setMessage(actions, taskTitle),
                    actionLabel = setActionLabel(actions),
                    duration = SnackbarDuration.Short
                )
                undoDeletedTask(
                    action = actions, snackBarResult = result, onUndoClicked = onUndoClicked
                )
            }
        }
    }
}

private fun setActionLabel(action: Action): String {
    return if (action.name == "DELETE") "Undo" else "Ok"
}

private fun undoDeletedTask(
    action: Action, snackBarResult: SnackbarResult, onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
        onUndoClicked(Action.UNDO)
    }
}

private fun setMessage(action: Action, taskTitle: String): String {
    return when (action) {
        Action.DELETE_ALL -> " All Message Deleted"
        else -> "${action.name} : $taskTitle"
    }
}

@Composable
fun ListFab(navigateToTaskScreen: (taskId: Int) -> Unit) {
    FloatingActionButton(onClick = { navigateToTaskScreen(-1) },) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button)
        )

    }
}

