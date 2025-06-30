package com.example.llmsparks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.llmsparks.data.model.Prompt
import com.example.llmsparks.repository.PromptRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*


class PromptViewModel(
    private val repository: PromptRepository
) : ViewModel() {

    val prompts: StateFlow<List<Prompt>> = repository.prompts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val bookmarkedIds: StateFlow<Set<String>> = repository.bookmarks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptySet())

    val bookmarkedPrompts: StateFlow<List<Prompt>> = combine(prompts, bookmarkedIds) { promptsList, bookmarkedSet ->
        promptsList.filter { it.id in bookmarkedSet }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun toggleBookmark(promptId: String) {
        viewModelScope.launch {
            repository.toggleBookmark(promptId)
        }
    }

    fun isBookmarked(promptId: String): Boolean {
        return bookmarkedIds.value.contains(promptId)
    }
}

