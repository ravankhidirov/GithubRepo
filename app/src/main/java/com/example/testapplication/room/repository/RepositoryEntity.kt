package com.example.testapplication.room.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repository_table")
data class RepositoryEntity(
    @PrimaryKey(autoGenerate = true)val id:Int,
    @ColumnInfo(name = "name")val name:String,
    @ColumnInfo(name = "language")val language: String?,
    @ColumnInfo(name = "avatar_url")val avatar_url:String?,
    @ColumnInfo(name = "forks")val forks:Int,
    @ColumnInfo(name = "description")val description:String?,
    @ColumnInfo(name="html_url")val html_url:String,
    @ColumnInfo(name="watch")val watch:Int,
)










