package com.example.todoapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.R
import com.example.todoapp.navigation.SetupNavigation
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.viewmodel.ToDoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    private val viewModel by viewModels<ToDoViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val checkSystemUi = isSystemInDarkTheme()
            val color by remember { mutableStateOf(checkSystemUi) }
            ToDoAppTheme {
                ChangeColor(state = color, activity = this)
                navHostController = rememberNavController()
                SetupNavigation(navHostController = navHostController,viewModel)

            }
        }
    }
}

@Composable
fun ChangeColor(state: Boolean, activity: ComponentActivity) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {

    }

    if (state) {

        WindowCompat.setDecorFitsSystemWindows(activity.window, true)
        activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.black)
    } else {
        WindowCompat.setDecorFitsSystemWindows(activity.window, true)
        activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.purple_500)
    }
}

