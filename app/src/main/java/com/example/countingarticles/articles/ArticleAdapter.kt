package com.example.countingarticles.articles

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.countingarticles.R
import com.example.countingarticles.database.Article
import com.example.countingarticles.databinding.ArticleItemListViewBinding


class ArticleAdapter(): ListAdapter<Article,ViewHolder>(ArticleDiffCallback())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.bind( articleItemList[position])
        holder.bind(getItem(position))
    }

}

class ViewHolder private constructor(val binding: ArticleItemListViewBinding) : RecyclerView.ViewHolder(binding.root){
    //val nameArticle: TextView = itemView.findViewById(R.id.article_name)
    //val priceArticle: TextView = itemView.findViewById(R.id.article_price)
    //val countArticle: TextView = itemView.findViewById(R.id.article_count)

    fun bind(article: Article) {
        binding.article = article

//        binding.articleCount.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                print(s.toString())
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                print(s.toString())
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                print(s.toString())
//            }
//        })
        binding.articleCount.doAfterTextChanged {
            print(it.toString())
        }


//        binding.articleName.text = article.articleName
//        binding.articlePrice.text = "$" + article.articlePrice.toString()
//        binding.articleCount.text = article.articleCount.toString()

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


