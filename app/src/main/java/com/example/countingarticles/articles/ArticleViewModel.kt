package com.example.countingarticles.articles

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countingarticles.database.ArticleDataBaseDAO
import com.example.countingarticles.model.ArticleModel
import com.example.countingarticles.model.ObjectBox
import io.objectbox.android.AndroidScheduler
import io.objectbox.query.Query
import io.objectbox.reactive.DataSubscription


class ArticleViewModel(
    val database: ArticleDataBaseDAO,
    application: Application): AndroidViewModel(application) {


    init {
       
    }

    fun setArticleItemList(articleItemList: List<ArticleModel>) {
       //All

    }

    override fun onCleared() {
        super.onCleared()
    }

    fun addArticle(name: String){
        val newArticle = ArticleModel(
            articleName = name,
            countArticle = 0,
            priceArticle = 0
        )
        print("Debug" + newArticle.articleName)
    }
}