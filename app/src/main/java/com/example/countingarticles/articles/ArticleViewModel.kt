package com.example.countingarticles.articles

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countingarticles.model.ArticleModel
import com.example.countingarticles.model.ObjectBox


class ArticleViewModel: ViewModel() {

    //private val articleBox = ObjectBox.boxStore.boxFor(ArticleViewModel::class.java)

    // The current word
//    private val _articles = MutableLiveData<List<ArticleModel>>()
//    val articles: LiveData<List<ArticleModel>>
//        get() = _articles
      val articles = MutableLiveData<List<ArticleModel>>()

    init {
        Log.i("ArticleViewModel", "ArticleViewModel created!")
        getAllArticles()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ArticleViewModel", "ArticleViewModel destroyed!")
    }

    fun getAllArticles(){

        //val liveDataA = MutableLiveData<List<ArticleModel>>()
        val articleItem = ArticleModel(
            articleName =  "pandora",
            countArticle = 10,
            priceArticle = 40
        )
        val articleItem1 = ArticleModel(
            articleName =  "pandora largo",
            countArticle = 5,
            priceArticle = 55
        )
//        articleBox.put(articleItem)
//        finish()
        articles.value = listOf(articleItem,articleItem1)
//        return liveDataA
    }
}