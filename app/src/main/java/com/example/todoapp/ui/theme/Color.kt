package com.example.todoapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Purple500 = Color(0xFF6200EE)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val lowPriorityColor = Color(0xFF00C980)
val mediumPriorityColor = Color(0xFFFFC114)
val highPriorityColor = Color(0xFFFF4646)
val nonePriorityColor = Color(0xFFFFFFFF)
val lightGray = Color(0xFFFCFCFC)
val mediumGray = Color(0xFF9C9C9C)
val darkGray = Color(0xFF141414)
val TopAppBarTextColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.White else lightGray
    val TopAppBarContainerColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else Purple500
val TaskItemBackGround
    @Composable get() = if (isSystemInDarkTheme()) darkGray else Color.White
val TaskItemTextColor
    @Composable get() = if (isSystemInDarkTheme()) lightGray else darkGray