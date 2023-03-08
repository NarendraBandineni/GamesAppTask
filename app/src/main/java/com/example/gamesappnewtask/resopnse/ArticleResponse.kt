package com.example.gamesappnewtask.resopnse

import com.example.gamesappnewtask.data.Article

data class ArticleResponse(
    val status:String,
    val totalResults: Int,
    val articles: List<Article>,
)
