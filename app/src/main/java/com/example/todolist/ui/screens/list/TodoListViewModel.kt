package com.example.todolist.ui.screens.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.model.Todo
import com.example.todolist.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TodoUiState {
    object Loading : TodoUiState()
    data class Success(val todos: List<Todo>) : TodoUiState()
    data class Error(val message: String) : TodoUiState()
}

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<TodoUiState>(TodoUiState.Loading)
    val uiState: StateFlow<TodoUiState> = _uiState.asStateFlow()

    init {
        Log.d("TodoListViewModel", "ViewModel initialized")
        initializeDatabase()
    }

    private fun initializeDatabase() {
        viewModelScope.launch {
            try {
                Log.d("TodoListViewModel", "Initializing database")
                repository.initializeDatabase()
                loadTodos()
            } catch (e: Exception) {
                Log.e("TodoListViewModel", "Failed to initialize database: ${e.message}", e)
                _uiState.value = TodoUiState.Error(e.message ?: "Failed to initialize database")
            }
        }
    }

    fun loadTodos() {
        viewModelScope.launch {
            try {
                Log.d("TodoListViewModel", "Loading todos")
                repository.refreshTodos()
                repository.todos
                    .catch { e ->
                        Log.e("TodoListViewModel", "Error in todos flow: ${e.message}", e)
                        _uiState.value = TodoUiState.Error(e.message ?: "Failed to load todos")
                    }
                    .collect { todos ->
                        Log.d("TodoListViewModel", "Collected ${todos.size} todos")
                        _uiState.value = TodoUiState.Success(todos ?: emptyList())
                    }
            } catch (e: Exception) {
                Log.e("TodoListViewModel", "Error refreshing todos: ${e.message}", e)
                _uiState.value = TodoUiState.Error(e.message ?: "Failed to refresh todos")
            }
        }
    }

    fun toggleTodoCompleted(todo: Todo) {
        viewModelScope.launch {
            try {
                Log.d("TodoListViewModel", "Toggling todo ${todo.id} completed: ${!todo.completed}")
                repository.updateTodo(todo.copy(completed = !todo.completed))
            } catch (e: Exception) {
                Log.e("TodoListViewModel", "Failed to toggle todo: ${e.message}", e)
                _uiState.value = TodoUiState.Error(e.message ?: "Failed to update todo")
            }
        }
    }

    fun addTodo(title: String, description: String?) {
        viewModelScope.launch {
            try {
                Log.d("TodoListViewModel", "Adding todo: $title")
                // Generate a temporary ID (replace with server ID if API supports creation)
                val newTodo = Todo(
                    id = (System.currentTimeMillis() % Int.MAX_VALUE).toInt(),
                    title = title,
                    description = description,
                    completed = false
                )
                repository.addTodo(newTodo)
            } catch (e: Exception) {
                Log.e("TodoListViewModel", "Failed to add todo: ${e.message}", e)
                _uiState.value = TodoUiState.Error(e.message ?: "Failed to add todo")
            }
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            try {
                Log.d("TodoListViewModel", "Deleting todo: ${todo.id}")
                repository.deleteTodo(todo)
            } catch (e: Exception) {
                Log.e("TodoListViewModel", "Failed to delete todo: ${e.message}", e)
                _uiState.value = TodoUiState.Error(e.message ?: "Failed to delete todo")
            }
        }
    }
}