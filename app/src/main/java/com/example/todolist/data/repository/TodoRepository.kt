package com.example.todolist.data.repository

import android.util.Log
import com.example.todolist.data.model.Todo
import com.example.todolist.data.source.local.TodoDao
import com.example.todolist.data.source.remote.TodoApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val todoDao: TodoDao,
    private val apiService: TodoApiService
) {
    val todos: Flow<List<Todo>> = todoDao.getAllTodos().also {
        Log.d("TodoRepository", "Initialized todos Flow")
    }

    suspend fun initializeDatabase() {
        withContext(Dispatchers.IO) {
            try {
                Log.d("TodoRepository", "Attempting to initialize database")
                todoDao.insertTodo(Todo(id = 0, title = "Test", description = "Test", completed = false))
                Log.d("TodoRepository", "Dummy todo inserted")
                todoDao.deleteAllTodos()
                Log.d("TodoRepository", "Database initialized")
            } catch (e: Exception) {
                Log.e("TodoRepository", "Failed to initialize database: ${e.message}", e)
            }
        }
    }

    suspend fun refreshTodos() {
        withContext(Dispatchers.IO) {
            try {
                Log.d("TodoRepository", "Fetching remote todos")
                val remoteTodos = apiService.getTodos()
                Log.d("TodoRepository", "Fetched ${remoteTodos.size} todos from API")
                if (remoteTodos.isNotEmpty()) {
                    Log.d("TodoRepository", "Deleting all todos")
                    todoDao.deleteAllTodos()
                    remoteTodos.forEach { todo ->
                        Log.d("TodoRepository", "Inserting todo: ${todo.id}, ${todo.title}")
                        todoDao.insertTodo(todo)
                    }
                } else {
                    Log.w("TodoRepository", "No todos received from API, inserting sample todos")
                    insertSampleTodos()
                }
            } catch (e: Exception) {
                Log.e("TodoRepository", "Failed to refresh todos: ${e.message}", e)
                insertSampleTodos()
            }
        }
    }

    private suspend fun insertSampleTodos() {
        try {
            Log.d("TodoRepository", "Inserting sample todos")
            val sampleTodos = listOf(
                Todo(1, "Sample Todo 1", "Description 1", false),
                Todo(2, "Sample Todo 2", "Description 2", true)
            )
            sampleTodos.forEach { todo ->
                Log.d("TodoRepository", "Inserting sample todo: ${todo.id}")
                todoDao.insertTodo(todo)
            }
        } catch (e: Exception) {
            Log.e("TodoRepository", "Failed to insert sample todos: ${e.message}", e)
        }
    }

    suspend fun getTodoById(id: Int): Todo? {
        return withContext(Dispatchers.IO) {
            Log.d("TodoRepository", "Getting todo by id: $id")
            todoDao.getTodoById(id)
        }
    }

    suspend fun addTodo(todo: Todo) {
        withContext(Dispatchers.IO) {
            Log.d("TodoRepository", "Adding todo: ${todo.id}, ${todo.title}")
            todoDao.insertTodo(todo)
        }
    }

    suspend fun updateTodo(todo: Todo) {
        withContext(Dispatchers.IO) {
            Log.d("TodoRepository", "Updating todo: ${todo.id}")
            todoDao.updateTodo(todo)
        }
    }

    suspend fun deleteTodo(todo: Todo) {
        withContext(Dispatchers.IO) {
            Log.d("TodoRepository", "Deleting todo: ${todo.id}")
            todoDao.deleteTodo(todo)
        }
    }
}