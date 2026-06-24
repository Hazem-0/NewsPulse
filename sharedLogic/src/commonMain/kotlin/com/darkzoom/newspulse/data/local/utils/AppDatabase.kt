package com.darkzoom.newspulse.data.local.utils

import androidx.room.Database
import com.darkzoom.newspulse.data.local.dao.ArticleDao
import com.darkzoom.newspulse.data.local.entity.ArticleEntity
import androidx.room.RoomDatabase

@Database(entities = [ArticleEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}