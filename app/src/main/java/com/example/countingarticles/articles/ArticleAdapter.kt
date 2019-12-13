package com.example.countingarticles.articles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.countingarticles.ItemArticleViewHolder
import com.example.countingarticles.R
import com.example.countingarticles.model.ArticleModel
import kotlinx.android.synthetic.main.article_item_list_view.view.*
import kotlinx.android.synthetic.main.item_article_list_view.view.*

class ArticleAdapter: RecyclerView.Adapter<ViewHolder>()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return articleItemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articleItemList[position])
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