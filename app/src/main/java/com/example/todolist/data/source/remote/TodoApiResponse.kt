package com.example.todolist.data.source.remote

import com.example.todolist.data.model.Todo

data class TodoApiResponse(
    val id: Int,
    val title: String,
    val completed: Boolean
) {
    fun toTodo(): Todo = Todo(id, title, null, completed)
}