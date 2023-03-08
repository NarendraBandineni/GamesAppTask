package com.example.gamesappnewtask.repository

import com.example.gamesappnewtask.api.APIService
import com.example.gamesappnewtask.utils.RetrofitUtil
import com.example.gamesappnewtask.resopnse.ArticleResponse

class APIRepo {
    private var apiService: APIService? = null
    init {
        apiService = RetrofitUtil.getRetrofit().create(APIService::class.java)
    }
    suspend fun getArticles(query: String, apiKey:String): ArticleResponse?{
        var articleResponse: ArticleResponse? = null
        apiService?.let { apiService ->
            articleResponse = apiService.getArticles(query,apiKey)
        }
        return articleResponse
    }
}