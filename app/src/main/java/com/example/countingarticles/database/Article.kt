package com.example.countingarticles.database


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_table")
data class Article(
    @PrimaryKey(autoGenerate = true)
    var Id: Long = 0L,

    @ColumnInfo(name = "article_name")
    val articleName: String = "",

    @ColumnInfo(name = "article_price")
    var articlePrice: Int = -1,

    @ColumnInfo(name = "article_count")
    var articleCount: Int = -1)