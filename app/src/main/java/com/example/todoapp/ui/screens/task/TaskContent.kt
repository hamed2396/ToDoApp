package com.example.todoapp.ui.screens.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.models.Priority
import com.example.todoapp.ui.componenet.PriorityDropDown
import com.example.todoapp.ui.theme.Typography
import com.example.todoapp.ui.theme.dimen.LARGE_PADDING
import com.example.todoapp.ui.theme.mediumGray
import com.example.todoapp.utils.Constants.MAX_TITLE_LENGTH

@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit,
    topPadding: Dp
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = LARGE_PADDING)
            .padding(top = topPadding)
            .padding(bottom = 8.dp)
    ) {

        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = mediumGray),
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { if (it.length <= MAX_TITLE_LENGTH) onTitleChange(it) },
            label = { Text(text = stringResource(R.string.title)) },
            textStyle = Typography.bodyLarge,
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        PriorityDropDown(priority = priority, onPrioritySelected = onPrioritySelected)
        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = mediumGray),
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { onDescriptionChange(it) },
            label = { Text(text = stringResource(R.string.description)) },
            textStyle = Typography.bodyLarge,

            )

    }
}

@Preview
@Composable
private fun TaskContentPrev() {
    TaskContent(
        title = "SHOP",
        onTitleChange = {},
        description = "buy for parrot",
        onDescriptionChange = { },
        priority = Priority.HIGH, {}, 10.dp
    )
}