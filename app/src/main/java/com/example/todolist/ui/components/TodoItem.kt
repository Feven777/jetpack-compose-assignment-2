package com.example.todolist.ui.components

import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import com.example.todolist.data.model.Todo

@Composable
fun TodoItem(
    todo: Todo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(8.dp)
    ) {
        Checkbox(
            checked = todo.completed,
            onCheckedChange = null, // Read-only; update via ViewModel
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = todo.title,
            modifier = Modifier.weight(1f)
        )
    }
}