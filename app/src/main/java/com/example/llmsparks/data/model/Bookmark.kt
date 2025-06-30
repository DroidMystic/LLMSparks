package com.example.llmsparks.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class Bookmark(@PrimaryKey val promptId: String)
