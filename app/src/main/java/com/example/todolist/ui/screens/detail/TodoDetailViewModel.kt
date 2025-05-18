package com.example.todolist.ui.screens.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.model.Todo
import com.example.todolist.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
    private val _todo = MutableStateFlow<Todo?>(null)
    val todo: StateFlow<Todo?> = _todo.asStateFlow()

    fun loadTodo(id: Int) {
        viewModelScope.launch {
            try {
                Log.d("TodoDetailViewModel", "Loading todo: $id")
                val loadedTodo = repository.getTodoById(id)
                _todo.value = loadedTodo
            } catch (e: Exception) {
                Log.e("TodoDetailViewModel", "Failed to load todo: ${e.message}", e)
            }
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            try {
                Log.d("TodoDetailViewModel", "Updating todo: ${todo.id}")
                repository.updateTodo(todo)
            } catch (e: Exception) {
                Log.e("TodoDetailViewModel", "Failed to update todo: ${e.message}", e)
            }
        }
    }
}