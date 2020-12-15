package com.example.countingarticles.addArticle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countingarticles.database.Article
import com.example.countingarticles.database.ArticleDataBaseDAO
import kotlinx.coroutines.*

class AddArticleViewModel(
    private val articleKey: Long = 0L,
    dataSource: ArticleDataBaseDAO) : ViewModel() {

    val database = dataSource

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val article: LiveData<Article>

    fun getArticle() = article

    init {
        article=database.getArticleWithId(articleKey)
    }

    private val _navigateToAddArticle = MutableLiveData<Boolean?>()
    val navigateToAddArticle: LiveData<Boolean?>
        get() = _navigateToAddArticle

    fun doneNavigating() {
        _navigateToAddArticle.value = null
    }

    fun onClose() {
        _navigateToAddArticle.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun addArticle(name: String,price: Double, count:Int){
        uiScope.launch {
            val newArticle = Article(
                articleName = name,
                articlePrice =  price,
                articleCount = count
            )
            insert(newArticle)
        }
    }

    private suspend fun insert(article: Article) {
        withContext(Dispatchers.IO) {
            database.insert(article)
        }
    }

    fun updateArticle(name: String, price: Double, count:Int){
        uiScope.launch {
            val oldArticle = article.value ?: return@launch
            oldArticle.articleName = name
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


    fun removeArticle() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                if(articleKey.toInt() != -1)
                    database.delete(articleKey)
            }
        }
    }
}