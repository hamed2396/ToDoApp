package com.example.todoapp.ui.screens.task

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.todoapp.data.models.ToDoTask
import com.example.todoapp.utils.Action
import com.example.todoapp.viewmodel.ToDoViewModel
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun AddTaskScreen(
    navigateToTaskScreen: (Action) -> Unit,
    selectedTask: ToDoTask?,
    viewModel: ToDoViewModel
) {
   BackHandler {
       navigateToTaskScreen(Action.NO_ACTION)
   }
    val context = LocalContext.current
    val title by viewModel.title
    val desc by viewModel.description
    val priority by viewModel.priority
    Scaffold(topBar = {
        TaskAppBar(
            navigateToListScreen = { action ->
                if (action == Action.NO_ACTION) {
                    navigateToTaskScreen(action)

                } else {
                    if (viewModel.validateFields()) {
                        navigateToTaskScreen(action)
                    } else {
                        Toast.makeText(context, "Fields Empty", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            selectedTask = selectedTask
        )
    })
    {
        TaskContent(
            title = title,
            onTitleChange = { title -> viewModel.title.value = title },
            description = desc,
            onDescriptionChange = { desc -> viewModel.description.value = desc },
            priority = priority,
            onPrioritySelected = { priority -> viewModel.priority.value = priority },
            it.calculateTopPadding()
        )
    }
}

