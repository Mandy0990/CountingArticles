package com.example.countingarticles.addArticle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.countingarticles.database.ArticleDataBaseDAO

class AddArticleViewModelFactory(
    private val articleKey: Long,
    private val dataSource: ArticleDataBaseDAO) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddArticleViewModel::class.java)) {
            return AddArticleViewModel(articleKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}