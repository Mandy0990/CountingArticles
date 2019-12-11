package com.example.countingarticles.model

import android.app.Application

class ArticleApp: Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}