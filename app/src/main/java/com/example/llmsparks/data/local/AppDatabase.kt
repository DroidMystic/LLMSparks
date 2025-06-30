package com.example.llmsparks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.llmsparks.data.model.Bookmark
import com.example.llmsparks.data.model.Prompt


@Database(
    entities = [Prompt::class, Bookmark::class],
    version = 2,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun promptDao(): PromptDao

}