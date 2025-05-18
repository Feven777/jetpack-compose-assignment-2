package com.example.todolist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String? = null,
    val completed: Boolean = false
)