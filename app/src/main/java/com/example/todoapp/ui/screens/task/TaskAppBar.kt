package com.example.todoapp.ui.screens.task

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.R
import com.example.todoapp.data.models.ToDoTask
import com.example.todoapp.ui.componenet.DisplayAlertDialog
import com.example.todoapp.ui.theme.TopAppBarContainerColor
import com.example.todoapp.ui.theme.TopAppBarTextColor
import com.example.todoapp.utils.Action

@Composable
fun TaskAppBar(navigateToListScreen: (Action) -> Unit,selectedTask: ToDoTask?) {
    if (selectedTask==null){
        NewTaskAppBar(navigateToListScreen)
    }else{
        ExistingAppBar(navigateToListScreen,selectedTask)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskAppBar(navigateToListScreen: (Action) -> Unit) {

    TopAppBar(
        title =
        {
            Text(text = "Add Task", color = TopAppBarTextColor)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = TopAppBarContainerColor),
        navigationIcon = { BackAction(navigateToListScreen) },
        actions = {

            AddAction(navigateToListScreen)
        }
    )
}

@Composable
fun BackAction(onBackClicked: (Action) -> Unit) {

    IconButton(onClick = { onBackClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(
                R.string.back_arrow
            ),
            tint = TopAppBarTextColor
        )
    }

}

@Composable
fun AddAction(onAddClicked: (Action) -> Unit) {

    IconButton(onClick = { onAddClicked(Action.ADD) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(
                R.string.add_task
            ),
            tint = TopAppBarTextColor
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExistingAppBar(navigateToListScreen: (Action) -> Unit,selectedTask: ToDoTask) {
    TopAppBar(
        title =
        {
            Text(text = "Add Task", color = TopAppBarTextColor)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = TopAppBarContainerColor),
        navigationIcon = {CloseAction(navigateToListScreen) },
        actions = {
            ExistingTaskAppBarActionsS(
                navigateToListScreen = navigateToListScreen,
                selectedTask =selectedTask )



        }
    )
}

@Composable
fun ExistingTaskAppBarActionsS(navigateToListScreen: (Action) -> Unit, selectedTask: ToDoTask) {
    var dialogState by remember {
        mutableStateOf(false)
    }
    DisplayAlertDialog(
        title = stringResource(id = R.string.remove_task,selectedTask.title),
        message =stringResource(id = R.string.remove_task_confirm,selectedTask.title),
        openDialog =dialogState,
        closeDialog = {dialogState=false },
        onYesClicked = {navigateToListScreen(Action.DELETE)}
    )
    UpdateAction(navigateToListScreen)
    DeleteAction { dialogState = true }
}
@Composable
fun CloseAction(onCloseClicked: (Action) -> Unit) {

    IconButton(onClick = { onCloseClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(
                R.string.close
            ),
            tint = TopAppBarTextColor
        )
    }

}
@Composable
fun UpdateAction(onUpdateClicked: (Action) -> Unit) {

    IconButton(onClick = {
        onUpdateClicked(Action.UPDATE) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(
                R.string.update_task
            ),
            tint = TopAppBarTextColor
        )
    }

}
@Composable
fun DeleteAction(onDeleteClicked: () -> Unit) {

    IconButton(onClick = { onDeleteClicked() }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(
                R.string.delete_task
            ),
            tint = TopAppBarTextColor
        )
    }

}

@Preview
@Composable
private fun AddTaskPrev() {
    NewTaskAppBar {

    }
}