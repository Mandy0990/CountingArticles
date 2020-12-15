package com.example.countingarticles.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class ArticleModel(
    @Id
    var id: Long = 0,
    val articleName: String,
    val countArticle: Int,
    val priceArticle: Double
)
