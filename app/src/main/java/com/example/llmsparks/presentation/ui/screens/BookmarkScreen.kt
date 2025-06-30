package com.example.llmsparks.presentation.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.llmsparks.presentation.viewmodel.PromptViewModel
import org.koin.androidx.compose.koinViewModel
import com.example.llmsparks.presentation.ui.components.PromptCard
import kotlinx.serialization.json.Json
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.serialization.encodeToString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    navController: NavController,
    vm: PromptViewModel = koinViewModel()
) {
    val bookmarkedPrompts by vm.bookmarkedPrompts.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Bookmarked Prompts") })
        }
    ) { paddingValues ->
        if (bookmarkedPrompts.isEmpty())
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No bookmarks yet")
            }
        else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                items(bookmarkedPrompts) { prompt ->
                    PromptCard(
                        prompt = prompt,
                        isBookmarked = true,
                        onBookmarkClick = { vm.toggleBookmark(prompt.id) },
                        onCardClick = {
                            val json = Uri.encode(Json.encodeToString(prompt))
                            navController.navigate("details/$json")
                        }
                    )
                }
            }
        }
    }
}