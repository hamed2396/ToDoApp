package com.example.todoapp.data.models


import androidx.compose.ui.graphics.Color
import com.example.todoapp.ui.theme.highPriorityColor
import com.example.todoapp.ui.theme.lowPriorityColor
import com.example.todoapp.ui.theme.mediumPriorityColor
import com.example.todoapp.ui.theme.nonePriorityColor

enum class Priority(val color: Color) {
    HIGH(highPriorityColor),
    MEDIUM(mediumPriorityColor),
    LOW(lowPriorityColor),
    NONE(nonePriorityColor)
}