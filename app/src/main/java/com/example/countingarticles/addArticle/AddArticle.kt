package com.example.countingarticles.addArticle


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.example.countingarticles.R
import com.example.countingarticles.databinding.FragmentAddArticleBinding
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.countingarticles.articles.ArticleFragmentDirections
import com.example.countingarticles.articles.ArticleViewModel
import com.example.countingarticles.articles.ArticleViewModelFactory
import com.example.countingarticles.database.Article
import com.example.countingarticles.database.ArticleDatabase
import kotlinx.android.synthetic.main.fragment_add_article.*

/**
 * A simple [Fragment] subclass.
 */
class AddArticle : Fragment() {

    private lateinit var viewModel: AddArticleViewModel
    private lateinit var binding: FragmentAddArticleBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_article,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val arguments = AddArticleArgs.fromBundle(arguments!!)

        // Create an instance of the ViewModel Factory.
        val dataSource = ArticleDatabase.getInstance(application).articleDatabaseDao
        val viewModelFactory = AddArticleViewModelFactory(arguments.articleKey.toLong(), dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(AddArticleViewModel::class.java)

        Log.i("ArticleFragment", "Called ViewModelProviders.of")

        binding.fab.setOnClickListener{ saveArticle() }

        viewModel.getArticle().observe(this, Observer {
            if (arguments.articleKey.toInt() != -1) { // Observed state is true.
                saveOrUpdateArticle(it)
            }
        })


        return binding.root
    }

    private fun saveOrUpdateArticle(article: Article) {
        if(article.Id != -1.toLong()) {
            Toast.makeText(context, article.articleName, Toast.LENGTH_LONG).show()
            putValueArticle(article.articleName,article.articlePrice,article.articleCount)
        }
    }

    private fun putValueArticle(articleName: String, articlePrice: Int, articleCount: Int) {
        binding.articleNameEdittext.setText(articleName)
        binding.articleCountEdittext.setText(articleCount.toString())
        binding.articlePriceEdittext.setText(articlePrice.toString())
    }

    private fun saveArticle(){
        if (article_name_edittext.text.isNullOrBlank()) {
            Toast.makeText(activity,"Please enter a name for articcle",
                Toast.LENGTH_SHORT)
                .show()
        } else {
            viewModel.addArticle(article_name_edittext.text.toString()
                                ,article_price_edittext.text.toString().toInt()
                                ,article_count_edittext.text.toString().toInt())
            val action =
                AddArticleDirections.nextActionToListArticle()
            findNavController().navigate(action)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_article, menu)
    }
}
