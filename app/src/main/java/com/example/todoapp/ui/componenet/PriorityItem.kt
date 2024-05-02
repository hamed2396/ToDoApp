package com.example.todoapp.ui.componenet

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.data.models.Priority
import com.example.todoapp.ui.theme.Typography
import com.example.todoapp.ui.theme.dimen.LARGE_PADDING
import com.example.todoapp.ui.theme.dimen.PRIORITY_INDICATOR_SIZE

@Composable
fun PriorityItem(priority: Priority) {
    Row {
        Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)) {
            drawCircle(color = priority.color)
        }
        Text(
            text = priority.name,
            style = Typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = LARGE_PADDING)
        )
    }
}

@Preview
@Composable
private fun PriorityItemPrev() {
    PriorityItem(priority = Priority.HIGH)
}