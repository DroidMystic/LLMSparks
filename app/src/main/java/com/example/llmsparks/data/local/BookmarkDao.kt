package com.example.llmsparks.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.llmsparks.data.model.Bookmark
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM bookmarks")
    fun getAllBookmarks(): Flow<List<Bookmark>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(b: Bookmark)

    @Delete
    suspend fun removeBookmark(b: Bookmark)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE promptId = :id)")
    suspend fun isBookmarked(id: String): Boolean
}
