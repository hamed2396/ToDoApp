package com.example.todoapp.ui.componenet

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.todoapp.R
import com.example.todoapp.ui.theme.Typography

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onYesClicked: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = closeDialog,
            confirmButton = {
                TextButton(
                    onClick = onYesClicked,
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                ) {
                    Text(
                        text = stringResource(R.string.yes)

                    )
                }
            },
            dismissButton = {
                TextButton(onClick = closeDialog) {
                    Text(
                        text = stringResource(R.string.no)

                    )
                }
            },
            title = {
                Text(
                    text = title,
                    fontSize = Typography.headlineLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = Typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Normal
                )
            }
        )
    }
}