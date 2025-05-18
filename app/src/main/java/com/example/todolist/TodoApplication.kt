package com.example.todolist

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp




@HiltAndroidApp
class TodoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("TodoApplication", "Application created")
    }
}