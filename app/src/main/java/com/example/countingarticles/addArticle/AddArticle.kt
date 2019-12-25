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
    private  var articleKey: Int = -1


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
        articleKey = arguments.articleKey

        // Create an instance of the ViewModel Factory.
        val dataSource = ArticleDatabase.getInstance(application).articleDatabaseDao
        val viewModelFactory = AddArticleViewModelFactory(arguments.articleKey.toLong(), dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(AddArticleViewModel::class.java)

        Log.i("ArticleFragment", "Called ViewModelProviders.of")

        binding.fab.setOnClickListener{ saveArticle(arguments.articleKey.toInt()) }

        viewModel.getArticle().observe(this, Observer {
            if (arguments.articleKey.toInt() != -1 && it != null) { // Observed state is true.
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

    private fun saveArticle(articleId: Int){
        if (article_name_edittext.text.isNullOrBlank()) {
            Toast.makeText(activity,"Please enter a name for articcle",
                Toast.LENGTH_SHORT)
                .show()
        } else {
            var name = article_name_edittext.text.toString()
            var price = if (article_price_edittext.text.toString() == " ") "0" else article_price_edittext.text.toString()
            var count = if (article_count_edittext.text.toString() == " ") "0" else article_count_edittext.text.toString()

            if(articleId == -1) {
                viewModel.addArticle(name,price.toInt(),count.toInt())
            }else{
                viewModel.updateArticle(name,price.toInt(),count.toInt())
            }
            navigateToListArticle()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_article, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.button_delete -> {
                if(articleKey == -1) {
                    Toast.makeText(
                        activity, "This article don't exit in DataBase",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else{
                    viewModel.removeArticle()
                    navigateToListArticle()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun navigateToListArticle(){
        val action =
            AddArticleDirections.nextActionToListArticle()
        findNavController().navigate(action)
    }
}
