package com.example.countingarticles.articles

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countingarticles.database.Article
import com.example.countingarticles.database.ArticleDataBaseDAO
import kotlinx.coroutines.*


class ArticleViewModel(
    val database: ArticleDataBaseDAO,
    application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val articles = database.getAllArticles()

    private var articleCurrent = MutableLiveData<Article?>()

    private val _articleToUpdate = MutableLiveData<Long>()
    val articleToUpdate
        get() = _articleToUpdate

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
//        uiScope.launch {
//            articleCurrent.value = getArticleCurrentFromDatabase()
//        }
    }

    private suspend fun getArticleCurrentFromDatabase(): Article? {
        return withContext(Dispatchers.IO) {
            var article = database.get(0)
            article
        }
    }

    fun onArticleClicked(id: Long) {
        _articleToUpdate.value = id
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun addArticle(name: String,price: Int, count:Int){

        uiScope.launch {
            val newArticle = Article(
                articleName = name,
                articlePrice =  price,
                articleCount = count
            )
            insert(newArticle)
            articleCurrent.value = getArticleCurrentFromDatabase()
        }

        //print("Debug" + newArticle.articleName)
    }

    private suspend fun insert(article: Article) {
        withContext(Dispatchers.IO) {
            database.insert(article)
        }
    }

    fun updateArticle(price: Int, count:Int){
        uiScope.launch {
            val oldArticle = articleCurrent.value ?: return@launch
            oldArticle.articlePrice = price
            oldArticle.articleCount = count
            update(oldArticle)
        }
    }

    private suspend fun update(article: Article) {
        withContext(Dispatchers.IO) {
            database.update(article)
        }
    }


}