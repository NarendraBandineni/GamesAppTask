package com.example.gamesappnewtask.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.gamesappnewtask.R
import com.example.gamesappnewtask.adapter.ArticlesAdapter
import com.example.gamesappnewtask.data.Article
import com.example.gamesappnewtask.databinding.FragmentAllArticlesBinding
import com.example.gamesappnewtask.resopnse.ArticleResponse
import com.example.gamesappnewtask.room.RoomUtil
import com.example.gamesappnewtask.room.data.Date
import com.example.gamesappnewtask.viewmodels.AllArticlesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AllArticlesFragment : Fragment() {
    private lateinit var binding: FragmentAllArticlesBinding
    private lateinit var viewModel: AllArticlesViewModel

    private var observer: Observer<ArticleResponse>? = null
    private val adapter: ArticlesAdapter by lazy { ArticlesAdapter(arrayListOf()) }
    private val searchAdapter: ArticlesAdapter by lazy { ArticlesAdapter(arrayListOf()) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAllArticlesBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[AllArticlesViewModel::class.java]

        initUI()

        return binding.root
    }

    private fun initUI() {

        binding.rv.layoutManager = WrapContentLinearLayoutManager(requireContext())
        binding.rv.adapter = adapter

        binding.rvSearch.layoutManager = WrapContentLinearLayoutManager(requireContext())
        binding.rvSearch.adapter = searchAdapter

        with(adapter) {
            onClick = { article ->
                val bundle = Bundle()
                bundle.putString("title", article.title)
                bundle.putString("author", article.author)
                bundle.putString("content", article.content)
                bundle.putString("description", article.description)
                bundle.putString("date", article.publishedAt)
                findNavController().navigate(
                    R.id.action_allArticlesFragment_to_detailPageFragment,
                    bundle
                )
            }
        }

        fetchArticles()
        observeArticles()

        binding.searchBtn.setOnClickListener {

            if(viewModel.inSearch){
                viewModel.inSearch = false
                binding.rvSearch.visibility = View.GONE
                binding.rv.visibility = View.VISIBLE
                binding.searchBtn.setImageResource(R.drawable.ic_search)
                updateAdapterData(emptyList(),searchAdapter)
                return@setOnClickListener
            }

            viewModel.inSearch = true
            val searchText = binding.searchText.text.toString()
            Log.d("SearchText",searchText)
            var filteredArticles: List<Article>? = null
            viewModel.articleRes?.let {
                it?.let {
                    filteredArticles = it.articles.filter {
                        it?.author?.contains(searchText) ?: false
                    }
                }
            }

            filteredArticles?.let {
                if(it.isNotEmpty()){
                    binding.rv.visibility = View.GONE
                    binding.rvSearch.visibility = View.VISIBLE

                    binding.searchBtn.setImageResource(R.drawable.ic_close)

                    updateAdapterData(it, searchAdapter)
                }
            }


        }
    }

    private fun observeArticles() {
        observer = Observer {
            it?.let {
                viewModel.articleRes = it
                val dates: List<Date> = it.articles.mapIndexed { index, item ->
                    Date(index, item.publishedAt)
                }.toList()

                saveDates(dates)

                updateAdapterData(it.articles, adapter)

            }
        }

        observer?.let {
            viewModel.articlesLiveData.observe(viewLifecycleOwner, it)
        }

    }

    private fun updateAdapterData(articles: List<Article>, adapter: ArticlesAdapter){
        adapter.articles = articles
        CoroutineScope(Dispatchers.IO).launch {
            adapter.articles.forEachIndexed { index, _ ->
                withContext(Dispatchers.Main) {
                    adapter.notifyItemChanged(index)
                }
            }
        }
    }

    private fun saveDates(dates: List<Date>) {
        val db = RoomUtil.getAppDatabase(requireActivity().applicationContext)
        val dateDao = db.dateDao()

        CoroutineScope(Dispatchers.IO).launch {
            dateDao.deleteAll()
            try {
                dates.forEach {
                    dateDao.insertDates(it)
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun fetchArticles() {
        with(viewModel) {
            getArticles("google", "0427abffae794f8395cc4fff93691bc2")
        }
    }

    class WrapContentLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
        //... constructor
        override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
            try {
                super.onLayoutChildren(recycler, state)
            } catch (e: IndexOutOfBoundsException) {
                Log.e("TAG", "meet a IOOBE in RecyclerView")
            }
        }
    }
}