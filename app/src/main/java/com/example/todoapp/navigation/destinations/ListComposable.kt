package com.example.todoapp.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todoapp.ui.screens.list.ListScreen
import com.example.todoapp.utils.Constants
import com.example.todoapp.utils.Constants.LIST_ARGUMENT_KEY
import com.example.todoapp.utils.toAction
import com.example.todoapp.viewmodel.ToDoViewModel


fun NavGraphBuilder.listComposable(navigateToTaskScreen: (Int) -> Unit, viewModel: ToDoViewModel) {
    composable(Constants.LIST_SCREEN, arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
        type = NavType.StringType
    }))
    { navBackStackEntry ->

        val action = navBackStackEntry.arguments!!.getString(LIST_ARGUMENT_KEY).toAction()
        LaunchedEffect(key1 = action) {
            viewModel.action.value=action
        }
        ListScreen(navigateToTaskScreen, viewModel)

    }


}

