package com.example.countingarticles.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.countingarticles.ItemArticleViewHolder
import com.example.countingarticles.R
import com.example.countingarticles.model.ArticleModel

class ArticleAdapter: RecyclerView.Adapter<ItemArticleViewHolder>()
{
    var data =  listOf<ArticleModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemArticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.item_article_list_view, parent, false) as TextView
        return ItemArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ItemArticleViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.articleName
    }

}