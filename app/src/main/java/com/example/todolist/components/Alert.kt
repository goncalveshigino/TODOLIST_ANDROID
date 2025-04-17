package com.example.todolist.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.todolist.ui.theme.TODOLISTTheme

@Composable
fun Alert(
    title: String,
    message: String,
    confirmText: String,
    onConfirmClick: () -> Unit,
    onDismiss: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = onConfirmClick) {
                Text(confirmText)
            }
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AlertPreview() {
    TODOLISTTheme {
        Alert(
            title = "Atenção",
            message = "Tem certeza que deseja sair?",
            confirmText = "Sim",
            onConfirmClick = {}
        )
    }
}
