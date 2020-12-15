package com.example.countingarticles.articles

import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Intent
import android.util.Log
import android.widget.Toast
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
    private val _articleIdToUpdate = MutableLiveData<Long>()

    init {
     //Todo
    }

    private suspend fun getArticleCurrentFromDatabase(): Article? {
        return withContext(Dispatchers.IO) {
            var article = database.get(0)
            article
        }
    }


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

    fun getTextToShareWithWatsApp(): String{
        var s = ""
        articles.let {
            for (art in it.value!!){
                if (art.articleCount != 0 ){
                    s += art.articlePrice.toString() + "$" + " " + art.articleCount.toString() + " " + art.articleName  + "\n"
                }
            }
        }
        return s
    }

    fun getTotalPriceArticle(): String{
        var total: Double = 0.0
        articles.let {
            for (art in it.value!!){
                if (art.articleCount != 0 ){
                    total += art.articlePrice * art.articleCount
                }
            }
        }
        return total.toString()
    }
}