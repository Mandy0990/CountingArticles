package com.example.countingarticles

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.countingarticles.model.ArticleModel
import kotlinx.android.synthetic.main.item_article_list_view.view.*

//class ItemArticleViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)

class ItemArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(articleItemModel: ArticleModel) {
        itemView.article_name_textview.text = articleItemModel.articleName
        itemView.setOnClickListener {
            //recyclerViewItemClickListener.onItemClicked(borrowedItemModel)
        }
    }
}