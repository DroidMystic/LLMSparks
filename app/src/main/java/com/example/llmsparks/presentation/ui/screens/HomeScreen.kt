package com.example.llmsparks.presentation.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.llmsparks.presentation.ui.components.PromptCard
import com.example.llmsparks.presentation.viewmodel.PromptViewModel
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    vm: PromptViewModel = koinViewModel()
) {
    val prompts by vm.prompts.collectAsState()
    val bookmarks by vm.bookmarkedIds.collectAsState()

    Scaffold {
        paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + 72.dp // 👈 extra bottom space
            )
        ) {
            items(prompts) { prompt ->
                PromptCard(
                    prompt = prompt,
                    isBookmarked = bookmarks.contains(prompt.id),
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


