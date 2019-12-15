package com.example.countingarticles.articles


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.example.countingarticles.R
import com.example.countingarticles.databinding.FragmentAddArticleBinding
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.countingarticles.database.ArticleDatabase
import com.example.countingarticles.model.ArticleModel
import com.example.countingarticles.model.ObjectBox
import kotlinx.android.synthetic.main.fragment_add_article.*

/**
 * A simple [Fragment] subclass.
 */
class AddArticle : Fragment() {

    private lateinit var viewModel: ArticleViewModel
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

        val dataSource = ArticleDatabase.getInstance(application).articleDatabaseDao

        val viewModelFactory = ArticleViewModelFactory(dataSource,application)

        Log.i("ArticleFragment", "Called ViewModelProviders.of")
        viewModel = ViewModelProviders.of(
            this,viewModelFactory).get(ArticleViewModel::class.java)

        binding.fab.setOnClickListener{ saveArticle() }
        return binding.root
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
            val action = ArticleFragmentDirections.nextAction()
            findNavController().navigate(action)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_article, menu)
    }
}
