package com.example.llmsparks.repository

import com.example.llmsparks.data.local.BookmarkDao
import com.example.llmsparks.data.local.PromptDao
import com.example.llmsparks.data.model.Bookmark
import com.example.llmsparks.data.model.Prompt
import com.example.llmsparks.data.remote.PromptRemoteDataSource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class PromptRepository(
    private val remote: PromptRemoteDataSource,
    private val promptDao: PromptDao,
    private val bookmarkDao: BookmarkDao
) {
    val prompts = channelFlow<List<Prompt>> {
        remote.getPromptsFlow()
            .onEach { list ->
                promptDao.clear()
                promptDao.insertAll(list)
                send(list)
            }
            .catch { promptDao.getAllPrompts().collect { send(it) } }
            .launchIn(this)
    }

    val bookmarks = bookmarkDao.getAllBookmarks()
        .map { it.map { b -> b.promptId }.toSet() }

    suspend fun toggleBookmark(promptId: String) =
        if (bookmarkDao.isBookmarked(promptId)) bookmarkDao.removeBookmark(Bookmark(promptId))
        else bookmarkDao.addBookmark(Bookmark(promptId))
}