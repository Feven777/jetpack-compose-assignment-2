package com.example.todolist.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.todolist.data.repository.TodoRepository
import com.example.todolist.data.source.local.TodoDatabase
import com.example.todolist.data.source.local.TodoDao
import com.example.todolist.data.source.remote.TodoApi
import com.example.todolist.data.source.remote.TodoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        Log.d("AppModule", "Providing OkHttpClient")
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideTodoDatabase(
        @ApplicationContext context: Context
    ): TodoDatabase {
        Log.d("AppModule", "Creating TodoDatabase")
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "todo_database"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideTodoDao(database: TodoDatabase): TodoDao {
        Log.d("AppModule", "Providing TodoDao")
        return database.todoDao()
    }

    @Provides
    @Singleton
    fun provideTodoApi(client: OkHttpClient): TodoApi {
        Log.d("AppModule", "Providing TodoApi")
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTodoApiService(todoApi: TodoApi): TodoApiService {
        Log.d("AppModule", "Providing TodoApiService")
        return TodoApiService(todoApi)
    }

    @Provides
    @Singleton
    fun provideTodoRepository(
        todoDao: TodoDao,
        apiService: TodoApiService
    ): TodoRepository {
        Log.d("AppModule", "Providing TodoRepository")
        return TodoRepository(todoDao, apiService)
    }
}