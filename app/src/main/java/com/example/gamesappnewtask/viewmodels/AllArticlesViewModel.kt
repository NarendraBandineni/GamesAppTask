package com.example.gamesappnewtask.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamesappnewtask.repository.APIRepo
import com.example.gamesappnewtask.resopnse.ArticleResponse
import kotlinx.coroutines.*

class AllArticlesViewModel: ViewModel() {
    var articleRes: ArticleResponse? = null
    private var apiRepo: APIRepo? = null
    var inSearch = false
    val articlesLiveData: MutableLiveData<ArticleResponse> by lazy { MutableLiveData<ArticleResponse>() }
    init {
        apiRepo = APIRepo()
    }
    fun getArticles(query:String, apiKey: String){
        viewModelScope.launch {
           withContext(Dispatchers.IO){
               launch {
                   val result = apiRepo?.getArticles(query,apiKey)
                   articlesLiveData.postValue(result)
               }
           }
        }
    }
}