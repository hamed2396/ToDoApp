package com.example.todoapp.ui.screens.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.models.Priority
import com.example.todoapp.ui.componenet.DisplayAlertDialog
import com.example.todoapp.ui.componenet.PriorityItem
import com.example.todoapp.ui.theme.TopAppBarContainerColor
import com.example.todoapp.ui.theme.TopAppBarTextColor
import com.example.todoapp.ui.theme.Typography
import com.example.todoapp.ui.theme.dimen.LARGE_PADDING
import com.example.todoapp.utils.Action
import com.example.todoapp.utils.SearchAppBarState
import com.example.todoapp.utils.TrailingIconState
import com.example.todoapp.utils.logError
import com.example.todoapp.viewmodel.ToDoViewModel

@Composable
fun ListAppBar(
    viewModel: ToDoViewModel, searchAppBarState: SearchAppBarState, searchTextState: String
) {

    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchClicked = {
                    viewModel.searchAppBarState.value = SearchAppBarState.OPENED

                },
                onDeleteAllClicked = { viewModel.action.value = Action.DELETE_ALL },
                onSortClicked = {viewModel.highPriorityTask.value.logError()
                    viewModel.persistSortState(priority = it)
                }
            )
        }

        else -> {
            SearchAppBar(text = searchTextState,
                onValueChange = { newText -> viewModel.searchTextState.value = newText },
                onSearchClicked = {
                    viewModel.searchTask(it)
                },
                onCloseClicked = {
                    viewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                    viewModel.searchTextState.value = ""
                })
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (priority: Priority) -> Unit,
    onDeleteAllClicked: () -> Unit
) {
    TopAppBar(title = { Text(text = "Tasks") }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = TopAppBarContainerColor, titleContentColor = TopAppBarTextColor
    ), actions = {
        AppBarActions(onSearchClicked, onSortClicked, onDeleteAllClicked)
    })
}

@Composable
fun AppBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (priority: Priority) -> Unit,
    onDeleteAllClicked: () -> Unit
) {
    var dialogState by remember {
        mutableStateOf(false)
    }
    DisplayAlertDialog(
        title = stringResource(id = R.string.remove_all_task),
        message = stringResource(id = R.string.remove_all_task_confirm),
        openDialog =dialogState,
        closeDialog = {dialogState=false },
        onYesClicked = {
            onDeleteAllClicked()
            dialogState=false
        }
    )
    SearchAction(onSearchClicked)
    SortAction(onSortClicked)
    DeleteAction { dialogState = true }
}

@Composable
fun SearchAction(onSearchClicked: () -> Unit) {
    IconButton(onClick = onSearchClicked) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(id = R.string.search),
            tint = TopAppBarTextColor
        )
    }
}

@Composable
fun SortAction(onSortClicked: (priority: Priority) -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_filter_list_24),
            contentDescription = stringResource(id = R.string.sort_actions),
            tint = TopAppBarTextColor
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { PriorityItem(priority = Priority.HIGH) }, onClick = {
                expanded = false
                onSortClicked(Priority.HIGH)
            })
            DropdownMenuItem(text = { PriorityItem(priority = Priority.LOW) }, onClick = {
                expanded = false
                onSortClicked(Priority.LOW)
            })
            DropdownMenuItem(text = { PriorityItem(priority = Priority.NONE) }, onClick = {
                expanded = false
                onSortClicked(Priority.NONE)
            })
        }
    }
}

@Composable
fun DeleteAction(onDeleteAllClicked: () -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(id = R.string.delete_all_task),
            tint = TopAppBarTextColor
        )
    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(text = {
            Text(
                text = stringResource(id = R.string.delete_all_task),
                style = Typography.bodyMedium,
                modifier = Modifier.padding(start = LARGE_PADDING),
                color = colorResource(id = android.R.color.holo_red_light)
            )
        }, onClick = {
            expanded = false
            onDeleteAllClicked()

        })

    }
}

@Composable
fun SearchAppBar(
    text: String,
    onValueChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    var state by remember {
        mutableStateOf(TrailingIconState.READY_TO_DELETE)
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp), color = TopAppBarContainerColor
    ) {
        TextField(
            value = text,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { onValueChange(it) },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search),
                    modifier = Modifier.alpha(.5f),
                    color = Color.White
                )
            },
            textStyle = TextStyle(fontSize = Typography.bodyLarge.fontSize, color = Color.White),
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.alpha(.5f)) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search),
                        tint = TopAppBarTextColor
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        when (state) {
                            TrailingIconState.READY_TO_DELETE -> {
                                if (text.isEmpty()) onCloseClicked()

                                onValueChange("")
                                state = TrailingIconState.READY_TO_CLOSE
                            }

                            TrailingIconState.READY_TO_CLOSE -> {
                                if (text.isEmpty()) {

                                    onCloseClicked()
                                    state = TrailingIconState.READY_TO_DELETE

                                } else {
                                    onValueChange("")
                                }
                            }
                        }
                    }, modifier = Modifier.alpha(.5f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.close),
                        tint = TopAppBarTextColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearchClicked(text) }),
            colors = TextFieldDefaults.colors(
                cursorColor = TopAppBarTextColor,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = TopAppBarContainerColor,
                focusedContainerColor = TopAppBarContainerColor,


                )
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ListAppBarPrev() {
    SearchAppBar(text = "", onValueChange = {}, onSearchClicked = {}) {

    }
}