package com.example.countingarticles.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ArticleDataBaseDAO{
    @Insert
    fun insert(article: Article)

    @Update
    fun update(article: Article)

    @Query("SELECT * from article_table WHERE Id = :key")
    fun get(key: Long): Article?

    @Query("DELETE FROM article_table WHERE Id = :key")
    fun delete(key: Long)

    @Query("DELETE FROM article_table")
    fun clear()

    @Query("SELECT * FROM article_table ORDER BY article_name ASC")
    fun getAllArticles(): LiveData<List<Article>>

    @Query("SELECT * from article_table WHERE Id = :key")
    fun getArticleWithId(key: Long): LiveData<Article>

}