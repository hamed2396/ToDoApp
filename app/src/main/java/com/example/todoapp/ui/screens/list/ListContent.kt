package com.example.todoapp.ui.screens.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.models.Priority
import com.example.todoapp.data.models.ToDoTask
import com.example.todoapp.ui.theme.TaskItemBackGround
import com.example.todoapp.ui.theme.TaskItemTextColor
import com.example.todoapp.ui.theme.Typography
import com.example.todoapp.ui.theme.dimen.LARGEST_PADDING
import com.example.todoapp.ui.theme.dimen.LARGE_PADDING
import com.example.todoapp.ui.theme.dimen.PRIORITY_INDICATOR_SIZE
import com.example.todoapp.ui.theme.highPriorityColor

import com.example.todoapp.utils.Action
import com.example.todoapp.utils.RequestState
import com.example.todoapp.utils.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ListContent(
    allTasks: RequestState<List<ToDoTask>>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    topPaddingValues: Dp,
    searchedTask: RequestState<List<ToDoTask>>,
    searchAppBarState: SearchAppBarState,
    lowPriorityTask: List<ToDoTask>,
    highPriorityTask: List<ToDoTask>,
    sortState: RequestState<Priority>,
    onSwipeToDelete: (action: Action, task: ToDoTask) -> Unit
) {
    if (sortState is RequestState.Success) {
        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchedTask is RequestState.Success) {
                    HandleListContent(
                        toDoTask = searchedTask.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        topPadding = topPaddingValues,
                        onSwipeToDelete = onSwipeToDelete
                    )
                }
            }

            sortState.data == Priority.NONE -> {
                if (allTasks is RequestState.Success) {
                    HandleListContent(
                        toDoTask = allTasks.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        topPadding = topPaddingValues,
                        onSwipeToDelete = onSwipeToDelete
                    )
                }
            }

            sortState.data == Priority.LOW -> {

                HandleListContent(
                    toDoTask = lowPriorityTask,
                    navigateToTaskScreen = navigateToTaskScreen,
                    topPadding = topPaddingValues,
                    onSwipeToDelete = onSwipeToDelete
                )
            }

            sortState.data == Priority.HIGH -> {

                HandleListContent(
                    toDoTask = highPriorityTask,
                    navigateToTaskScreen = navigateToTaskScreen,
                    topPadding = topPaddingValues,
                    onSwipeToDelete = onSwipeToDelete
                )
            }
        }
    }


}

@Composable
fun Item(toDoTask: ToDoTask, navigateToTaskScreen: (taskId: Int) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = TaskItemBackGround,
        shadowElevation = 2.dp,
        onClick = { navigateToTaskScreen(toDoTask.id) }
    ) {
        Column(
            modifier = Modifier
                .padding(LARGE_PADDING)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    text = toDoTask.title,
                    color = TaskItemTextColor,
                    style = Typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1, modifier = Modifier.fillMaxWidth(.9f)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)) {
                        drawCircle(color = toDoTask.priority.color)
                    }
                }
            }
            Text(
                text = toDoTask.description,
                modifier = Modifier.fillMaxWidth(),
                color = TaskItemTextColor,
                style = Typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HandleListContent(
    toDoTask: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    topPadding: Dp,
    onSwipeToDelete: (action: Action, task: ToDoTask) -> Unit
) {
    if (toDoTask.isNotEmpty()) {
        LazyColumn(modifier = Modifier.padding(top = topPadding)) {
            items(items = toDoTask, key = {
                it.id
            }) { task ->
                val scope = rememberCoroutineScope()
                LaunchedEffect(true) {
                    scope.launch {
                        delay(300)
                    }
                }

                var itemAppeared by remember {
                    mutableStateOf(false)
                }

                LaunchedEffect(true) {
                    itemAppeared = true
                }
                val dismissState =
                    rememberSwipeToDismissBoxState(positionalThreshold = { it.div(3) })

                LaunchedEffect(key1 = dismissState.currentValue) {
                    if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
                        onSwipeToDelete(Action.DELETE, task)
                    }
                }
                val degrees by
                animateFloatAsState(
                    targetValue =
                    if (dismissState.targetValue == SwipeToDismissBoxValue.Settled)
                        0f else -45f,
                    label = ""
                )
                AnimatedVisibility(
                    visible = itemAppeared, enter = expandVertically(
                        animationSpec = tween(300)
                    ),
                    exit = shrinkVertically(tween(300))

                ) {
                    SwipeToDismissBox(
                        state = dismissState,
                        enableDismissFromStartToEnd = false,
                        backgroundContent = { RedBackGround(degrees = degrees) }
                    ) {
                        Item(toDoTask = task, navigateToTaskScreen)
                    }
                }




            }
        }
    } else {
        EmptyContent()
    }
}

@Composable
fun RedBackGround(modifier: Modifier = Modifier, degrees: Float) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(highPriorityColor)
            .padding(horizontal = LARGEST_PADDING),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_task),
            tint = Color.White
        )
    }
}

@Preview
@Composable
private fun ItemPrev() {
    RedBackGround(degrees = 45f)
}