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
import com.example.countingarticles.model.ArticleModel
import kotlinx.coroutines.*


class ArticleViewModel(
    val database: ArticleDataBaseDAO,
    application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

//    val articles = database.getAllArticles()
    private val _articleIdToUpdate = MutableLiveData<Long>()

    val listArticles: MutableLiveData<List<Article>> by lazy {
        MutableLiveData<List<Article>>()
    }

    init {
        this.listArticles.apply { postValue(emptyList()) }
    }

    private suspend fun getArticleCurrentFromDatabase(): Article? {
        return withContext(Dispatchers.IO) {
            return@withContext  database.get(0)
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
        var total: Double = 0.0
        listArticles.let {
            for (art in it.value!!){
                if (art.articleCount != 0 ){
                    s += "$" + art.articlePrice.toString() + " - " + art.articleCount.toString() + " " + art.articleName  + "\n"
                    total += art.articlePrice * art.articleCount
                }
            }
        }
        s += "Total: " + "$" + total.toString()
        return s
    }

    fun getTotalPriceArticle(value:String, articlePosition: Int, typeField:String): String?{
        var total: Double = 0.0

        if(value != "") {
            if (typeField == "price") {
                listArticles.value?.get(articlePosition)?.articlePrice = value.toDouble()
            } else {
                listArticles.value?.get(articlePosition)?.articleCount = value.toInt()
            }


            listArticles.let {
                for (art in it.value!!) {
                    if (art.articleCount != 0 && art.articlePrice != 0.0) {
                        total += art.articlePrice * art.articleCount
                    }
                }
            }
            return total.toString()
        }
        return null
    }
    //** CRUD BD **

    fun addNewArticle(){
        val newArticle = Article(
            articleName = "",
            articlePrice =  0.0,
            articleCount = 0
        )
        this.listArticles.apply { postValue(listArticles.value?.plus(newArticle)) }

//        uiScope.launch {
//            val newArticle = Article(
//                articleName = "Pandora",
//                articlePrice =  0.0,
//                articleCount = 0
//            )
//             insert(newArticle)
//        }
    }

    private suspend fun insert(article: Article) {
        withContext(Dispatchers.IO) {
            database.insert(article)
        }
    }

    fun removeAllArticles() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                database.clear()
            }
        }
    }
}