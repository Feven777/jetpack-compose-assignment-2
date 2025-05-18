package com.example.todolist.data.source.local


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.data.model.Todo

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,  // Changed from String to Int
    val title: String,
    val description: String = "",
    val completed: Boolean = false
) {
    fun toTodo(): Todo = Todo(id, id.toString(), title, completed)
}

fun Todo.toEntity(): TodoEntity = TodoEntity(id, id.toString(), title, completed)