package com.example.todolist.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Define Deep Purple theme colors
val DeepPurplePrimary = Color(0xFF673AB7)  // Deep Purple (Main Theme Color)
val DeepPurpleSecondary = Color(0xFF9575CD)  // Lighter Purple Shade
val DeepPurpleTertiary = Color(0xFFD1C4E9)  // Soft Accent
val DeepPurpleBackground = Color(0xFF311B92)  // Dark Purple
val DeepPurpleSurface = Color(0xFF512DA8)  // Mid-tone Purple
val DeepPurpleOnPrimary = Color.White

// Light theme colors
private val LightColorScheme = lightColorScheme(
    primary = DeepPurplePrimary,
    secondary = DeepPurpleSecondary,
    tertiary = DeepPurpleTertiary,
    background = Color(0xFFF3E5F5), // Soft Lavender background
    surface = DeepPurpleSurface,
    onPrimary = DeepPurpleOnPrimary
)

// Dark theme colors
private val DarkColorScheme = darkColorScheme(
    primary = DeepPurplePrimary,
    secondary = DeepPurpleSecondary,
    tertiary = DeepPurpleTertiary,
    background = DeepPurpleBackground,
    surface = DeepPurpleSurface,
    onPrimary = DeepPurpleOnPrimary
)

@Composable
fun TodoListTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}