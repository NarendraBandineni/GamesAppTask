package com.example.gamesappnewtask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gamesappnewtask.R
import com.example.gamesappnewtask.data.Article
import com.example.gamesappnewtask.utils.DateUtil
import com.google.android.material.textview.MaterialTextView

class ArticlesAdapter(var articles: List<Article>): RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder>() {

    var onClick: ((Article) -> (Unit))? = null

    class ArticlesViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var title: MaterialTextView? = null
        var author: MaterialTextView? = null
        var date: MaterialTextView? = null
        var image: ImageView? = null
        init {
            title = view.findViewById(R.id.title)
            author = view.findViewById(R.id.author)
            date = view.findViewById(R.id.date)
            image = view.findViewById(R.id.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_layout,parent,false)
        return ArticlesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val article = articles[position]
        holder.title?.apply {
            text = article.title
        }
        holder.author?.apply {
            text = article.author
        }

        holder.date?.apply {
            text = DateUtil.getDateInDDMMYYYY(article.publishedAt)
        }

        holder.image?.let { imageView ->
            Glide.with(holder.itemView.context)
                .load(article.urlToImage)
                .centerCrop()
                .into(imageView)
        }

        holder.itemView.setOnClickListener {
            onClick?.invoke(articles[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}