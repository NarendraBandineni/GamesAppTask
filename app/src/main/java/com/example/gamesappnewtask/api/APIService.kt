package com.example.gamesappnewtask.api

import com.example.gamesappnewtask.resopnse.ArticleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("v2/everything")
    suspend fun getArticles(@Query("q") query: String, @Query("apiKey")apiKey: String): ArticleResponse
}