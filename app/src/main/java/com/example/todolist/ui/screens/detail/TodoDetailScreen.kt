package com.example.todolist.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.todolist.data.model.Todo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    navController: NavHostController,
    todoId: Int,
    viewModel: TodoDetailViewModel = hiltViewModel()
) {
    val todo by viewModel.todo.collectAsState()

    LaunchedEffect(todoId) {
        viewModel.loadTodo(todoId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo Details",color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge
                ) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary

                        )
                    }
                }
            )
        }
    ) { padding ->
        todo?.let { t ->
            var title by remember { mutableStateOf(t.title) }
            var description by remember { mutableStateOf(t.description ?: "") }
            var completed by remember { mutableStateOf(t.completed) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = completed,
                        onCheckedChange = { completed = it }
                    )
                    Text("Completed")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        viewModel.updateTodo(
                            t.copy(
                                title = title,
                                description = description.ifBlank { null },
                                completed = completed
                            )
                        )
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = title.isNotBlank()
                ) {
                    Text("Save")
                }
            }
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Todo not found")
            }
        }
    }
}