package com.example.countingarticles

import android.view.View
import android.widget.TextView
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