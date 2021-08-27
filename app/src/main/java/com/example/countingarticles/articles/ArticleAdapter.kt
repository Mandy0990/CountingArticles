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


class ArticleAdapter(var onChangeValuesListener: OnChangeValuesListener?): ListAdapter<Article,ViewHolder>(ArticleDiffCallback())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent,onChangeValuesListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.bind( articleItemList[position])
        holder.bind(getItem(position))
    }

}

class ViewHolder private constructor(val binding: ArticleItemListViewBinding, val onChangeValuesListener: OnChangeValuesListener?) : RecyclerView.ViewHolder(binding.root){
    init {
        binding.articleCount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                println(s)
                s?.let {
                    onChangeValuesListener?.onOnChangeValuesListener(s.toString(),adapterPosition,"count")
                }
            }
        })

        binding.articlePrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                println(s)

                s?.let {
                    onChangeValuesListener?.onOnChangeValuesListener(s.toString(),adapterPosition,"price")
                }
            }
        })

        binding.articleName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                println(s)
            }
        })
    }

    fun bind(article: Article) {
        binding.article = article
        binding.articleName.setText(article.articleName)

        var textPrice = if (article.articlePrice == 0.0 )  "" else article.articlePrice.toString()
        binding.articlePrice.setText(textPrice)

        var textCount = if (article.articleCount == 0 )  "" else article.articleCount.toString()
        binding.articleCount.setText(textCount)

        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup,onChangeValuesListener: OnChangeValuesListener?): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ArticleItemListViewBinding.inflate(layoutInflater,parent,false)
            return ViewHolder(binding,onChangeValuesListener)
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

interface OnChangeValuesListener  {
    fun onOnChangeValuesListener(value:String, articlePosition: Int, typeField: String)
}


