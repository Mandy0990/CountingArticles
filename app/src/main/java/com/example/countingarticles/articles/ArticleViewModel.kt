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

    private val _articleIdToUpdate = MutableLiveData<Long>()
    val articleIdToUpdate
        get() = _articleIdToUpdate

    fun onArticleClicked(id: Long) {
        _articleIdToUpdate.value = id
    }

    fun onAddArticleNavigated() {
        _articleIdToUpdate.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
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