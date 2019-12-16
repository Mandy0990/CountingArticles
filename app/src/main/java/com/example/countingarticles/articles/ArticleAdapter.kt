package com.example.countingarticles.articles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.countingarticles.R
import com.example.countingarticles.database.Article
import com.example.countingarticles.databinding.ArticleItemListViewBinding


class ArticleAdapter( val clickListener: ArticleListener): ListAdapter<Article,ViewHolder>(ArticleDiffCallback())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.bind( articleItemList[position])
        holder.bind(getItem(position),clickListener)
    }

}

class ViewHolder private constructor(val binding: ArticleItemListViewBinding) : RecyclerView.ViewHolder(binding.root){
    //val nameArticle: TextView = itemView.findViewById(R.id.article_name)
    //val priceArticle: TextView = itemView.findViewById(R.id.article_price)
    //val countArticle: TextView = itemView.findViewById(R.id.article_count)

    fun bind(article: Article,clickListener: ArticleListener) {
        binding.article = article
        binding.clickListener = clickListener

        binding.articleName.text = article.articleName
        binding.articlePrice.text = article.articlePrice.toString()+ "$"
        binding.articleCount.text = article.articleCount.toString()
        //itemView.setOnClickListener {
            //recyclerViewItemClickListener.onItemClicked(borrowedItemModel)
        //}
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ArticleItemListViewBinding.inflate(layoutInflater,parent,false)
            return ViewHolder(binding)
        }
    }
}

class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {

    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.Id == newItem.Id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}

class ArticleListener(val clickListener: (articleId: Long) -> Unit) {
    fun onClick(article: Article) = clickListener(article.Id)
}


