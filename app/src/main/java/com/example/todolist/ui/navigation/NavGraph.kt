//
//
//package com.example.todolist.ui.navigation
//
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NamedNavArgument
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import androidx.navigation.navArgument
//import com.example.todolist.ui.screens.detail.TodoDetailScreen
//import com.example.todolist.ui.screens.list.TodoListScreen
//
//@Composable
//fun NavGraph(navController: NavHostController = rememberNavController()) {
//    NavHost(navController = navController, startDestination = "todo_list") {
//        composable("todo_list") {
//            TodoListScreen(
//                onTodoClick = { todoId ->
//                    navController.navigate("todo_detail/$todoId")
//                }
//            )
//        }
//        composable(
//            route = "todo_detail/{todoId}",
//            arguments = this.arguments,
//            deepLinks = TODO(),
//            enterTransition = TODO(),
//            exitTransition = TODO(),
//            popEnterTransition = TODO(),
//            popExitTransition = TODO(),
//            content = TODO()