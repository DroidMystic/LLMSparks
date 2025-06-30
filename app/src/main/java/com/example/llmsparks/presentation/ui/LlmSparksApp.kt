package com.example.llmsparks.presentation.ui

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.*
import androidx.compose.runtime.getValue;
import androidx.navigation.navArgument
import com.example.llmsparks.data.model.Prompt
import com.example.llmsparks.presentation.ui.screens.BookmarkScreen
import com.example.llmsparks.presentation.ui.screens.PromptDetailsScreen
import com.example.llmsparks.presentation.ui.screens.HomeScreen
import kotlinx.serialization.json.Json


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LlmSparksApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val current by navController.currentBackStackEntryAsState()
                val route = current?.destination?.route
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, null) }, selected = route == "home",
                    onClick = { navController.navigate("home") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, null) }, selected = route == "bookmarks",
                    onClick = { navController.navigate("bookmarks") }
                )
            }
        }) {
        NavHost(navController, startDestination = "home") {
            composable("home") { HomeScreen(navController) }
            composable("bookmarks") { BookmarkScreen(navController) }
            composable(
                "details/{json}",
                arguments = listOf(navArgument("json") { type = NavType.StringType })
            ) {
                val json = Uri.decode(it.arguments?.getString("json").orEmpty())
                val prompt = Json.decodeFromString<Prompt>(json)
                PromptDetailsScreen(prompt, navController::popBackStack)
            }
        }
    }
}