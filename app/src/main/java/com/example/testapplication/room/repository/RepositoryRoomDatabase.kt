package com.example.testapplication.room.repository

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RepositoryEntity::class], version = 2)
abstract class RepositoryRoomDatabase: RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}