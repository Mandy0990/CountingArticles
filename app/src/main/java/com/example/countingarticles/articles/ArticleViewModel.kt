package com.example.countingarticles.articles

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countingarticles.model.ArticleModel
import com.example.countingarticles.model.ObjectBox
import io.objectbox.android.AndroidScheduler
import io.objectbox.query.Query
import io.objectbox.reactive.DataSubscription


class ArticleViewModel: ViewModel() {

    private val articleBox = ObjectBox.boxStore.boxFor(ArticleModel::class.java)
    lateinit var articleViewAdapter: ArticleAdapter
    private lateinit var articleQuery: Query<ArticleModel>
    private lateinit var subscription: DataSubscription

    // The current article
    private val _articles = MutableLiveData<List<ArticleModel>>()
    val articles: LiveData<List<ArticleModel>>
        get() = _articles

    init {
        Log.i("ArticleViewModel", "ArticleViewModel created!")
        articleViewAdapter = ArticleAdapter()
        articleQuery = articleBox.query().build()
        subscription = articleQuery
            .subscribe()
            .on(AndroidScheduler.mainThread())
            .observer {article -> articleViewAdapter.setArticleItemList(article) }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ArticleViewModel", "ArticleViewModel destroyed!")
        subscription.cancel()
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
        _articles.value = listOf(articleItem,articleItem1)
//        return liveDataA
    }

    fun addArticle(name: String){
        val newArticle = ArticleModel(
            articleName = name,
            countArticle = 0,
            priceArticle = 0
        )
        print("Debug" + newArticle.articleName)
        articleBox.put(newArticle)
    }
}