package com.example.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.todoapp.navigation.destinations.listComposable
import com.example.todoapp.navigation.destinations.taskComposable
import com.example.todoapp.utils.Constants
import com.example.todoapp.viewmodel.ToDoViewModel

@Composable
fun SetupNavigation(navHostController: NavHostController, viewModel: ToDoViewModel) {
    val screens = remember {
        AppScreens(navHostController)
    }
    NavHost(navController = navHostController, startDestination = Constants.LIST_SCREEN) {

        listComposable(screens.taskScreen,viewModel)
        taskComposable(screens.listScreen,viewModel)
    }
}