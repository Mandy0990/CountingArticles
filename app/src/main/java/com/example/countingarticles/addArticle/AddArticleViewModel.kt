package com.example.countingarticles.addArticle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countingarticles.database.Article
import com.example.countingarticles.database.ArticleDataBaseDAO
import kotlinx.coroutines.Job

class AddArticleViewModel(
    private val articleKey: Long = 0L,
    dataSource: ArticleDataBaseDAO) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via its SleepDatabaseDao.
     */
    val database = dataSource

    /** Coroutine setup variables */

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()

    private val article: LiveData<Article>

    fun getNight() = article

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
}