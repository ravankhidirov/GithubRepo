package com.example.testapplication.room.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RepositoryDao {
    @Query("Select * from repository_table")
    fun getAll(): List<RepositoryEntity>

    @Insert
    fun addFav(vararg fav: RepositoryEntity)

    @Delete
    fun delete(fav: RepositoryEntity)
}
