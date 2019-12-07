package com.example.countingarticles.articles

import android.util.Log
import androidx.lifecycle.ViewModel

class ArticleViewModel: ViewModel() {

    init {
        Log.i("ArticleViewModel", "ArticleViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ArticleViewModel", "ArticleViewModel destroyed!")
    }
}