package com.example.countingarticles.articles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.countingarticles.R
import com.example.countingarticles.model.ArticleModel
import kotlinx.android.synthetic.main.article_item_list_view.view.*
import kotlinx.android.synthetic.main.item_article_list_view.view.*


class ArticleAdapter: ListAdapter<ArticleModel,ViewHolder>(ArticleDiffCallback())
{
    private val articleItemList = mutableListOf<ArticleModel>()

//    fun setArticleItemList(articleItemList: List<ArticleModel>) {
//        this.articleItemList.clear()
//        this.articleItemList.addAll(articleItemList)
//        //notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.bind( articleItemList[position])
        holder.bind(getItem(position))
    }

}

class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
    val nameArticle: TextView = itemView.findViewById(R.id.article_name)
    val priceArticle: TextView = itemView.findViewById(R.id.article_price)
    val countArticle: TextView = itemView.findViewById(R.id.article_count)

    fun bind(articleItemModel: ArticleModel) {
        nameArticle.text = articleItemModel.articleName
        priceArticle.text = articleItemModel.priceArticle.toString()
        countArticle.text = articleItemModel.countArticle.toString()
        itemView.setOnClickListener {
            //recyclerViewItemClickListener.onItemClicked(borrowedItemModel)
        }
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.article_item_list_view, parent, false)
            return ViewHolder(view)
        }
    }
}

class ArticleDiffCallback : DiffUtil.ItemCallback<ArticleModel>() {

    override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
        return oldItem == newItem
    }
}