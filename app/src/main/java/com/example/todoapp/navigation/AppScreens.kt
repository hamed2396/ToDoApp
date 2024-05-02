package com.example.todoapp.navigation

import androidx.navigation.NavController
import com.example.todoapp.utils.Action
import com.example.todoapp.utils.Constants.LIST_SCREEN

class AppScreens(navController: NavController) {

    val listScreen: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }
    val taskScreen: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }
}