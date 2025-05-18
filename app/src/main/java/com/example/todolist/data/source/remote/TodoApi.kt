package com.example.todolist.data.source.remote

import retrofit2.http.GET

interface TodoApi {
    @GET("todos")
    suspend fun getTodos(): List<TodoApiResponse>
}