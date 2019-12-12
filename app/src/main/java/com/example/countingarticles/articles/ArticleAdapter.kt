package com.example.countingarticles.articles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.countingarticles.ItemArticleViewHolder
import com.example.countingarticles.R
import com.example.countingarticles.model.ArticleModel

class ArticleAdapter: RecyclerView.Adapter<ItemArticleViewHolder>()
{
    private val articleItemList = mutableListOf<ArticleModel>()

//    var data =  listOf<ArticleModel>()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }

    fun setArticleItemList(articleItemList: List<ArticleModel>) {
        this.articleItemList.clear()
        this.articleItemList.addAll(articleItemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemArticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.item_article_list_view, parent, false) as TextView
        return ItemArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleItemList.size
    }

    override fun onBindViewHolder(holder: ItemArticleViewHolder, position: Int) {
        holder.bind(articleItemList[position])
//        val item = articleItemList[position]
//        holder.textView.text = item.articleName
    }



}