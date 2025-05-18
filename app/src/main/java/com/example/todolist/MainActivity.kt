package com.example.todolist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolist.ui.screens.detail.TodoDetailScreen
import com.example.todolist.ui.screens.list.TodoListScreen
import com.example.todolist.ui.theme.TodoListTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx. navigation. NavType

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "MainActivity created")
        setContent {
            TodoListTheme {
                TodoApp()
            }
        }
    }
}

@Composable
fun TodoApp(navController: NavHostController = rememberNavController()) {
    Log.d("MainActivity", "Setting up navigation")
    NavHost(navController = navController, startDestination = "todo_list") {
        composable("todo_list") {
            TodoListScreen(
                onTodoClick = { id ->
                    navController.navigate("todo_detail/$id")
                }
            )
        }
        composable(
            "todo_detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getInt("id") ?: 0
            TodoDetailScreen(navController = navController, todoId = todoId)
        }
    }
}