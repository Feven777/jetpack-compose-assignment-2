package com.example.todolist.data.source.remote

import android.util.Log
import com.example.todolist.data.model.Todo
import javax.inject.Inject

class TodoApiService @Inject constructor(
    private val todoApi: TodoApi
) {
    suspend fun getTodos(): List<Todo> {
        try {
            val response = todoApi.getTodos()
            Log.d("TodoApiService", "API returned ${response.size} todos")
            return response.map { it.toTodo() }
        } catch (e: Exception) {
            Log.e("TodoApiService", "API call failed: ${e.message}", e)
            return emptyList()
        }
    }
}